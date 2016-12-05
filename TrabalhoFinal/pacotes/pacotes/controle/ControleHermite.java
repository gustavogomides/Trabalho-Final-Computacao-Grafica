package pacotes.controle;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ControleHermite {

	// DECLARACAO DA VARIAVEL
	public ArrayList<Point> pontosHermite = new ArrayList<>(); // ARRAY COM OS
																// PONTOS DA
																// FIGURA

	// *************************************************************************************
	// METODO QUE DEFINE OS PONTOS DA FIGURA E CHAMA O METODO DE DESENHO
	// PARAMETROS:
	// -> g: variavel do tipo Graphicsg
	// -> animacao: se true anima o desenho da figura
	// -> inserir: se true insere no array de pontos
	// -> controlePrograma: variavel do tipo ControleAplicativo
	// -> controleAnimacao: variavel para a classe ControleAnimacao
	public void hermite(Graphics g, boolean animacao, boolean inserir, ControleAplicativo controlePrograma,
			ControleAnimacao controleAnimacao) {
		Point inicio, fim, tangente1, tangente2;
		controleAnimacao.maiorX = controleAnimacao.maiorY = 0;
		controleAnimacao.menorX = controleAnimacao.menorY = 1000000;

		inicio = new Point(546, 130);
		tangente1 = new Point(546, 130);
		fim = new Point(560, 190);
		tangente2 = new Point(560, 190);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(560, 190);
		tangente1 = new Point(590, 160);
		fim = new Point(600, 190);
		tangente2 = new Point(590, 195);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(600, 190);
		tangente1 = new Point(600, 190);
		fim = new Point(614, 130);
		tangente2 = new Point(614, 130);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(614, 130);
		tangente1 = new Point(700, 795);
		fim = new Point(726, 138);
		tangente2 = new Point(608, -40);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(726, 138);
		tangente1 = new Point(1330, 420);
		fim = new Point(772, 460);
		tangente2 = new Point(230, 700);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(772, 460);
		tangente1 = new Point(850, 134);
		fim = new Point(683, 410);
		tangente2 = new Point(560, 500);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(683, 410);
		tangente1 = new Point(517, 144);
		fim = new Point(580, 490);
		tangente2 = new Point(532, 613);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(580, 490);
		tangente1 = new Point(486, 144);
		fim = new Point(477, 410);
		tangente2 = new Point(360, 582);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(477, 410);
		tangente1 = new Point(132, 134);
		fim = new Point(388, 460);
		tangente2 = new Point(404, 500);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(388, 460);
		tangente1 = new Point(-180, 200);
		fim = new Point(432, 138);
		tangente2 = new Point(1020, -150);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(432, 138);
		tangente1 = new Point(480, 750);
		fim = new Point(545, 146);
		tangente2 = new Point(520, 0);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

		inicio = new Point(545, 146);
		tangente1 = new Point(545, 146);
		fim = new Point(546, 130);
		tangente2 = new Point(546, 130);
		desenhoHermite(inicio, fim, tangente1, tangente2, g, animacao, inserir, controlePrograma, controleAnimacao);

	}

	// *************************************************************************************
	// METODO QUE CALCULA E DESENHA A CURVA
	// PARAMETROS:
	// -> inicio: ponto inicial da curva
	// -> fim: ponto final da curva
	// -> tangente1: ponto da tangente 1
	// -> tangente2: ponto da tangente2
	// -> g: variavel do tipo Graphics
	// -> animacao: se true anima o desenho da figura
	// -> inserir: se true insere no array de pontos
	// -> controlePrograma: variavel do tipo ControleAplicativo
	public void desenhoHermite(Point inicio, Point fim, Point tangente1, Point tangente2, Graphics g, boolean animacao,
			boolean inserir, ControleAplicativo controlePrograma, ControleAnimacao controleAnimacao) {

		Util util = new Util();

		for (int i = 0; i < 1000; i++) {
			int x = 0, y = 0;
			double t = (double) i / (double) 1000;
			double hp1 = 0.0, hp2 = 0.0, ht1 = 0.0, ht2 = 0.0;
			hp1 = (2 * t * t * t) - (3 * t * t) + 1;

			hp2 = (-2 * t * t * t) + (3 * t) * t;

			ht1 = (t * t * t) - (2 * t * t) + t;

			ht2 = (t * t * t) - (t * t);

			x = ((int) (hp1 * inicio.getX() + ht1 * (tangente1.getX() - inicio.getX())
					+ ht2 * (tangente2.getX() - fim.getX()) + hp2 * fim.getX()));

			y = ((int) (hp1 * inicio.getY() + ht1 * (tangente1.getY() - inicio.getY())
					+ ht2 * (tangente2.getY() - fim.getY()) + hp2 * fim.getY()));

			util.plotarPonto(g, (int) x, (int) y, false);

			atualizaBoundingBox(x, y, controleAnimacao);

			if (inserir) {
				pontosHermite.add(new Point((int) x, (int) y));
			}

			if (animacao) {
				util.sleep(1, controlePrograma);
			}
		}
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
	// METODO QUE RETORNA O ARRAY DE PONTOS DA FIGURA
	// RETORNO -> ARRAY DE PONTOS
	public ArrayList<Point> getArrayDesenhoHermite() {
		return pontosHermite;
	}

	// *************************************************************************************
}
