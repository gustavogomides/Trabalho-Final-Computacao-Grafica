package pacotes.controle;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ControleTransformacao {

	// ****************************************************************************************
	// METODO QUE IMPLEMENTA A TRANSFORMACAO ESCALA
	// PARAMETROS:
	// -> fatorEscala: fator de escala
	// -> pontosOriginais: array de pontos do desenho
	// -> g: variável do tipo Graphics
	// -> inserir: se true irá modificar o array original
	// -> imprimir: se true irá imprimir o desenho na tela
	// -> preencher: se true irá preencher o desenho
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public void escala(double fatorEscala, ArrayList<Point> pontosOriginais, Graphics g, boolean modificar,
			boolean imprimir, boolean preencher, ControleAnimacao controleAnimacao) {
		Point aux, aux2;

		double m[][] = { { fatorEscala, 0, 0 }, { 0, fatorEscala, 0 }, { 0, 0, 1 } };

		if (!modificar) {
			Point aux3;
			for (Point p : pontosOriginais) {
				aux = calcPontoTranslacao(-1 * controleAnimacao.centroX, -1 * controleAnimacao.centroY, p);
				aux2 = multiplicarMatrizes(m, aux);
				aux3 = calcPontoTranslacao(controleAnimacao.centroX, controleAnimacao.centroY, aux2);
				p.setLocation(aux3.x, aux3.y);
			}
			controleAnimacao.atualizaBoundingBox(pontosOriginais);
			controleAnimacao.atualizaCentroGeometrico(pontosOriginais);

			if (preencher) {
				new ControlePreenchimento().preencherFigura(g, pontosOriginais);
			}

			if (imprimir) {
				controleAnimacao.desenharFigura(pontosOriginais, g);
			}
		} else {
			ArrayList<Point> auxiliar = new ArrayList<>();

			for (Point p : pontosOriginais) {
				aux = calcPontoTranslacao(-1 * controleAnimacao.centroX, -1 * controleAnimacao.centroY, p);
				aux2 = multiplicarMatrizes(m, aux);
				auxiliar.add(calcPontoTranslacao(controleAnimacao.centroX, controleAnimacao.centroY, aux2));
			}
			controleAnimacao.atualizaBoundingBox(auxiliar);
			controleAnimacao.atualizaCentroGeometrico(auxiliar);

			if (preencher) {
				new ControlePreenchimento().preencherFigura(g, auxiliar);
			}

			if (imprimir) {
				controleAnimacao.desenharFigura(auxiliar, g);
			}
		}
	}

	// ****************************************************************************************
	// METODO QUE CALCULA A TRANSLACAO DO PONTO
	public Point calcPontoTranslacao(int h, int v, Point p) {
		double m[][] = { { 1, 0, h }, { 0, 1, v }, { 0, 0, 1 } };

		return multiplicarMatrizes(m, p);
	}

	// ****************************************************************************************
	// METODO QUE MULTIPLICA MATRIZES
	public Point multiplicarMatrizes(double m[][], Point p) {
		double x = 0, y = 0;

		double mr[][] = new double[3][1];
		double soma = 0.0;
		double m2[][] = { { p.getX() }, { p.getY() }, { 1 } };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 1; j++) {
				soma = 0;
				for (int k = 0; k < 3; k++) {
					soma += m[i][k] * m2[k][j];
				}
				mr[i][j] = soma;
			}
		}

		x = mr[0][0];
		y = mr[1][0];

		return new Point((int) x, (int) y);
	}

	// ****************************************************************************************
	// METODO QUE IMPLEMENTA A TRANSFORMACAO ROTACAO
	// PARAMETROS:
	// -> ponto: ponto a ser rotacionado
	// -> angulo: angulo de rotacao
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public Point rotacao(Point ponto, double angulo, ControleAnimacao controleAnimacao) {
		Point aux, aux2;
		int xp, yp;
		xp = controleAnimacao.centroX;
		yp = controleAnimacao.centroY;
		double teta = (angulo * Math.PI) / 180;

		double m[][] = { { Math.cos(teta), Math.sin(teta), 0 }, { -1 * Math.sin(teta), Math.cos(teta), 0 },
				{ 0, 0, 1 } };

		aux = calcPontoTranslacao(-1 * xp, -1 * yp, ponto);
		aux2 = multiplicarMatrizes(m, aux);

		return calcPontoTranslacao(xp, yp, aux2);
	}

	// ****************************************************************************************
	// METODO QUE IMPLEMENTA A TRANSFORMACAO TRANSLACAO
	// PARAMETROS:
	// -> h: fator do deslocamento horizontal
	// -> v: fator do deslocamento vertical
	// -> pontos: array de pontos do desenho
	// -> g: variável do tipo Graphics
	// -> imprimir: se true irá imprimir o desenho na tela
	// -> preencher: se true irá preencher o desenho
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public void translacao(int h, int v, ArrayList<Point> pontos, Graphics g, boolean imprimir, boolean preencher,
			ControleAnimacao controleAnimacao) {
		
		controleAnimacao.maiorX = controleAnimacao.maiorY = 0;
		controleAnimacao.menorX = controleAnimacao.menorY = 1000000;
		
		Point aux;
		for (Point p : pontos) {
			aux = calcPontoTranslacao(h, v, p);
			p.setLocation(aux.getX(), aux.getY());
		}

		controleAnimacao.atualizaBoundingBox(pontos);

		controleAnimacao.atualizaCentroGeometrico(pontos);

		if (imprimir) {
			controleAnimacao.desenharFigura(pontos, g);
		}

		if (preencher) {
			new ControlePreenchimento().preencherFigura(g, pontos);
		}

	}

}
