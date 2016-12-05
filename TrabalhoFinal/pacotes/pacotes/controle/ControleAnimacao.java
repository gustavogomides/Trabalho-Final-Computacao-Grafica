package pacotes.controle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

public class ControleAnimacao {

	// DECLARACAO DAS VARIAVEIS

	// VARIAVEIS QUE ARMAZENAM OS LIMITES DA BOUNDING BOX
	public int maiorX = 772;
	public int maiorY = 490;
	public int menorX = 388;
	public int menorY = 130;

	// COORDENADAS DO CENTRO GEOMETRICO DA FIGURA
	public int centroX = 580;
	public int centroY = 310;

	// VARIAVEL PARA A CLASSE ControleTransformacao
	private ControleTransformacao controleTransformacoes = new ControleTransformacao();

	// VARIAVEL PARA A CLASSE Util
	private Util util = new Util();

	// ****************************************************************************
	// METODO QUE ATUALIZA OS LIMITES DA BOUNDIG BOX
	public void atualizaBoundingBox(ArrayList<Point> pontos) {
		for (Point p : pontos) {
			if (p.getX() > maiorX)
				maiorX = (int) p.getX();

			if (p.getX() < menorX)
				menorX = (int) p.getX();

			if (p.getY() > maiorY)
				maiorY = (int) p.getY();

			if (p.getY() < menorY)
				menorY = (int) p.getY();
		}
	}

	// ****************************************************************************
	// METODO QUE ATUALIZA O CENTRO GEOMETRICO DA FIGURA
	public void atualizaCentroGeometrico(ArrayList<Point> pontos) {
		int xr = pontos.get(0).x, yrmax = pontos.get(0).y;
		int posymax = 0, posymin = 0, posx = 0;

		for (int i = 0; i < pontos.size(); i++) {
			if (pontos.get(i).getY() > yrmax) {
				yrmax = pontos.get(i).y;
				posx = i;
			}

			if (pontos.get(i).getX() > xr) {
				xr = pontos.get(i).x;
				posymax = i;
			} else {
				posymin = i;
			}

		}

		centroX = pontos.get(posx).x;
		centroY = (pontos.get(posymax).y + pontos.get(posymin).y) / 2;
	}

	// ****************************************************************************
	// METODO QUE DESENHA A FIGURA
	public void desenharFigura(ArrayList<Point> pontos, Graphics g) {
		Util util = new Util();

		for (Point p : pontos) {
			util.plotarPonto(g, p.x, p.y, false);
		}
	}

	// ****************************************************************************
	// METODO QUE TESTA SE OCORREU COLISAO COM AS BORDAS DO MONITOR
	// RETORNO:
	// 1 -> colidiu na borda de baixo
	// 2 -> colidiu na borda da direita
	// 3 -> colidiu na borda de cima
	// 4 -> colidiu na borda da esquerda
	public int testaColisão() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();

		if (maiorY >= dim.getHeight() - 170) {
			return 1;
		} else if (maiorX >= dim.getWidth() - 50) {
			return 2;
		} else if (menorY <= 40) {
			return 3;
		} else if (menorX <= 40) {
			return 4;
		} else {
			return 0;
		}
	}

	// ****************************************************************************
	// METODO QUE ROTACIONA OS PONTOS DA FIGURA
	public void rotacionar(ArrayList<Point> pontos, int angulo, Graphics g, boolean preencher) {
		ArrayList<Point> auxiliar = new ArrayList<Point>();

		for (Point p : pontos) {
			auxiliar.add(controleTransformacoes.rotacao(p, angulo, this));
		}

		atualizaBoundingBox(auxiliar);

		if (preencher) {
			new ControlePreenchimento().preencherFigura(g, auxiliar);
		}

		desenharFigura(auxiliar, g);

	}

	// ****************************************************************************
	// METODO PARA A ANIMACAO 1
	public void animacao1(ArrayList<Point> pontos, ControleAplicativo controlePrograma, Graphics g, boolean bezier) {

		ArrayList<Point> r = new ArrayList<Point>(pontos.size());
		for (Point o : pontos) {
			r.add((Point) o.clone());
		}

		int x = 50;
		int y = -5;
		int angulo = 0;
		double escala = 1.0;
		int contador = 0;

		int velocidade;

		if (bezier) {
			velocidade = 500;
		} else {
			velocidade = 700;
		}

		controleTransformacoes.escala(0.6, r, g, false, false, false, this);

		boolean vez = true;

		while (controlePrograma.runningAnimacao && contador < 6) {
			contador++;

			controlePrograma.setarLinha(true);
			if (vez) {
				vez = false;
				angulo = 0;
				rotacionar(r, angulo += 30, g, controlePrograma.preenchimento);
			} else {
				vez = true;
				angulo = 0;
				rotacionar(r, angulo -= 30, g, controlePrograma.preenchimento);
			}

			util.sleep(velocidade, controlePrograma);
		}

		contador = 0;
		vez = true;

		while (controlePrograma.runningAnimacao && contador < 6) {
			contador++;
			controlePrograma.setarLinha(true);
			if (vez) {
				vez = false;
				if (escala <= 2.0) {
					escala += 0.7;
				}
				controleTransformacoes.escala(escala, r, g, true, true, controlePrograma.preenchimento, this);
			} else {
				vez = true;
				if (escala >= 0.2) {
					escala -= 0.7;
				}
				controleTransformacoes.escala(escala, r, g, true, true, controlePrograma.preenchimento, this);
			}
			util.sleep(velocidade, controlePrograma);
		}

		contador = 0;
		vez = true;
		while (controlePrograma.runningAnimacao && contador <= 6) {
			contador++;

			controlePrograma.setarLinha(true);

			if (vez) {
				vez = false;
				controleTransformacoes.translacao(50, 0, r, g, true, controlePrograma.preenchimento, this);
			} else {
				vez = true;
				controleTransformacoes.translacao(-50, 0, r, g, true, controlePrograma.preenchimento, this);
			}

			util.sleep(velocidade, controlePrograma);
		}

		while (controlePrograma.runningAnimacao) {
			controlePrograma.setarLinha(true);

			controleTransformacoes.translacao(x, y, r, g, true, controlePrograma.preenchimento, this);

			if (testaColisão() == 2 || testaColisão() == 4) {
				x = -x;
			}

			if (testaColisão() == 1 || testaColisão() == 3) {
				y = -y;
			}

			util.sleep(velocidade, controlePrograma);
		}

	}

	// ****************************************************************************
	// METODO PARA A ANIMACAO 2
	public void animacao2(ArrayList<Point> pontos, ControleAplicativo controlePrograma, Graphics g) {

		ArrayList<Point> arrayPontos = new ArrayList<Point>(pontos.size());
		for (Point o : pontos) {
			arrayPontos.add((Point) o.clone());
		}

		int h = 30;
		int v = 50;
		int contador = 0;
		double escala = 0.9;
		int velocidade = 500;

		while (controlePrograma.runningAnimacao && contador < 4) {
			controlePrograma.setarLinha(true);
			contador++;
			controleTransformacoes.escala(escala, arrayPontos, g, false, true, controlePrograma.preenchimento, this);
			escala -= 0.1;

			util.sleep(velocidade, controlePrograma);
		}

		velocidade = 400;
		while (controlePrograma.runningAnimacao) {
			controlePrograma.setarLinha(true);

			controleTransformacoes.translacao(h, v, arrayPontos, g, true, controlePrograma.preenchimento, this);

			if (testaColisão() == 2 || testaColisão() == 4) {
				h = -h;
			}

			if (testaColisão() == 1 || testaColisão() == 3) {
				v = -v;
			}

			util.sleep(velocidade, controlePrograma);
		}

	}

}
