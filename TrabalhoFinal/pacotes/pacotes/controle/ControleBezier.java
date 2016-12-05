package pacotes.controle;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ControleBezier {

	// DECLARACAO DAS VARIAVEIS

	public ArrayList<Point> pontosBezier = new ArrayList<>(); // ARRAY COM OS
																// PONTOS DA
																// FIGURA

	private Util util = new Util();// VARIAVEL PARA A CLASSE UTIL

	// *************************************************************************************
	// METODO QUE DEFINE OS PONTOS DA FIGURA E CHAMA O METODO DE DESENHO
	// PARAMETROS:
	// -> g: variavel do tipo Graphicsg
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	// -> inserir: se true insere no array de pontos
	// -> animacao: se true anima o desenho da figura
	// -> controlePrograma: variavel do tipo ControleAplicativo
	public void bezier(Graphics g, ControleAnimacao controleAnimacao, boolean inserir, boolean animacao,
			ControleAplicativo controlePrograma) {

		Point inicio, fim, controle1, controle2;
		controleAnimacao.maiorX = controleAnimacao.maiorY = 0;
		controleAnimacao.menorX = controleAnimacao.menorY = 1000000;

		inicio = new Point(546, 130);
		fim = new Point(560, 190);
		desenhoBezier(inicio, fim, null, null, 2, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(560, 190);
		controle1 = new Point(580, 185);
		fim = new Point(600, 190);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(600, 190);
		fim = new Point(614, 130);
		desenhoBezier(inicio, fim, null, null, 2, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(614, 130);
		controle1 = new Point(614, 280);
		controle2 = new Point(740, 280);
		fim = new Point(730, 150);
		desenhoBezier(inicio, fim, controle1, controle2, 4, g, controleAnimacao, inserir, animacao, controlePrograma);

		/*
		 * gravar += "t = " + t + " ->\nC(" + t + ")(x) = (1 - " + t + ")³ * " +
		 * a1.x + " + 3 * " + t + " * (1 - " + t + ")² * " + b1.x + " + 3 * " +
		 * t + "² * (1 - " + t + ") * " + c1.x + " + " + t + "³ * " + d1.x +
		 * " = " + P.x + "\n";
		 * 
		 * gravar += "t = " + t + " ->\nC(" + t + ")(y) = (1 - " + t + ")³ * " +
		 * a1.y + " + 3 * " + t + " * (1 - " + t + ")² * " + b1.y + " + 3 * " +
		 * t + "² * (1 - " + t + ") * " + c1.y + " + " + t + "³ * " + d1.y +
		 * " = " + P.y + "\n\n";
		 */

		/*
		 * FileWriter arquivo;
		 * 
		 * try { arquivo = new FileWriter(new File("pacotes_30818\\eq.txt"));
		 * arquivo.write(gravar); arquivo.close(); } catch (IOException e) {
		 * e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
		 */

		inicio = new Point(730, 150);
		controle1 = new Point(728, 142);
		fim = new Point(726, 138);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, false, controlePrograma);

		inicio = new Point(726, 138);
		controle1 = new Point(1050, 320);
		fim = new Point(772, 460);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(772, 460);
		controle1 = new Point(800, 330);
		fim = new Point(683, 410);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(683, 410);
		controle1 = new Point(620, 320);
		fim = new Point(580, 490);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(580, 490);
		controle1 = new Point(540, 320);
		fim = new Point(477, 410);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(477, 410);
		controle1 = new Point(360, 320);
		fim = new Point(388, 460);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(388, 460);
		controle1 = new Point(110, 320);
		fim = new Point(432, 138);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, animacao, controlePrograma);

		inicio = new Point(432, 138);
		controle1 = new Point(428, 149);
		fim = new Point(430, 150);
		desenhoBezier(inicio, fim, controle1, null, 3, g, controleAnimacao, inserir, false, controlePrograma);

		inicio = new Point(430, 150);
		controle1 = new Point(420, 280);
		controle2 = new Point(546, 280);
		fim = new Point(546, 130);
		desenhoBezier(inicio, fim, controle1, controle2, 4, g, controleAnimacao, inserir, animacao, controlePrograma);
	}

	// *************************************************************************************
	// METODO QUE CALCULA E DESENHA A CURVA
	// PARAMETROS:
	// -> inicio: ponto inicial da curva
	// -> fim: ponto final da curva
	// -> controle1: ponto de controle 1
	// -> controle2: ponto de controle 2
	// -> tipo: tipo da curva (linear, quadrática, cúbica)
	// -> g: variavel do tipo Graphicsg
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	// -> inserir: se true insere no array de pontos
	// -> animacao: se true anima o desenho da figura
	// -> controlePrograma: variavel do tipo ControleAplicativo
	private void desenhoBezier(Point inicio, Point fim, Point controle1, Point controle2, int tipo, Graphics g,
			ControleAnimacao controleAnimacao, boolean inserir, boolean animacao, ControleAplicativo controlePrograma) {

		Point P = null;
		Point antigo = inicio;

		for (double t = 0.001; t < 1.0; t = t + 0.001) {
			if (tipo == 2) {
				P = curvaBezierLinear(inicio, fim, t, controleAnimacao);
			} else if (tipo == 3) {
				P = curvaBezierQuadratica(inicio, controle1, fim, t, controleAnimacao);
			} else if (tipo == 4) {
				P = curvaBezierCubica(inicio, controle1, controle2, fim, t, controleAnimacao);
			}

			util.plotarPonto(g, antigo.x, antigo.y, false);
			util.plotarPonto(g, P.x, P.y, false);

			antigo = P;

			if (inserir) {
				pontosBezier.add(new Point(antigo.x, antigo.y));
				pontosBezier.add(new Point(P.x, P.y));
			}

			if (animacao) {
				util.sleep(1, controlePrograma);
			}
		}
	}

	// *************************************************************************************
	// METODO QUE CALCULA A CURVA DE BEZIER LINEAR
	// PARAMETROS:
	// -> inicio: ponto inicial da curva
	// -> fim: ponto final da curva
	// -> t: instante t
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public Point curvaBezierLinear(Point inicio, Point fim, double t, ControleAnimacao controleAnimacao) {
		// c(t) = (1-t)P0 + tP1
		int x, y;

		x = (int) ((1 - t) * inicio.x + t * fim.x);
		y = (int) ((1 - t) * inicio.y + t * fim.y);

		atualizaBoundingBox(x, y, controleAnimacao);

		return new Point(x, y);
	}

	// *************************************************************************************
	// METODO QUE CALCULA A CURVA DE BEZIER QUADRATICA
	// PARAMETROS:
	// -> inicio: ponto inicial da curva
	// -> controle: ponto de controle
	// -> fim: ponto final da curva
	// -> t: instante t
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public Point curvaBezierQuadratica(Point inicio, Point controle, Point fim, double t,
			ControleAnimacao controleAnimacao) {
		// c(t) = (1-t)²P0 + 2t(1-t)P1 + t²P2
		int x, y;

		x = (int) ((1 - t) * (1 - t) * inicio.x + 2 * t * (1 - t) * controle.x + t * t * fim.x);
		y = (int) ((1 - t) * (1 - t) * inicio.y + 2 * t * (1 - t) * controle.y + t * t * fim.y);

		atualizaBoundingBox(x, y, controleAnimacao);

		return new Point(x, y);

	}

	// *************************************************************************************
	// METODO QUE CALCULA A CURVA DE BEZIER CUBICA
	// PARAMETROS:
	// -> inicio: ponto inicial da curva
	// -> controle1: ponto de controle 1
	// -> controle2: ponto de controle 2
	// -> fim: ponto final da curva
	// -> t: instante t
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public Point curvaBezierCubica(Point inicio, Point controle1, Point controle2, Point fim, double t,
			ControleAnimacao controleAnimacao) {
		// c(t) = (1-t)³P0 + 3t(1-t)²P1 + 3t²(1-t)P2 + t³P3
		int x, y;
		x = (int) ((1 - t) * (1 - t) * (1 - t) * inicio.x + 3 * t * (1 - t) * (1 - t) * controle1.x
				+ 3 * t * t * (1 - t) * controle2.x + t * t * t * fim.x);

		y = (int) ((1 - t) * (1 - t) * (1 - t) * inicio.y + 3 * t * (1 - t) * (1 - t) * controle1.y
				+ 3 * t * t * (1 - t) * controle2.y + t * t * t * fim.y);

		atualizaBoundingBox(x, y, controleAnimacao);

		return new Point(x, y);
	}

	// *************************************************************************************
	// METODO QUE ATUALIZA AS COORDENADAS DA BOUNDING BOX
	private void atualizaBoundingBox(int x, int y, ControleAnimacao controleAnimacao) {
		if (x > controleAnimacao.maiorX)
			controleAnimacao.maiorX = x;
		if (x < controleAnimacao.menorX)
			controleAnimacao.menorX = x;
		if (y > controleAnimacao.maiorY)
			controleAnimacao.maiorY = y;
		if (y < controleAnimacao.menorY)
			controleAnimacao.menorY = y;
	}

	// *************************************************************************************
	// METODO QUE RECUPERA OS PONTOS DA FIGURA
	// RETORNO -> ARRAY COM OS PONTOS DA FIGURA
	public ArrayList<Point> getArrayDesenhoBezier() {
		return pontosBezier;
	}

}
