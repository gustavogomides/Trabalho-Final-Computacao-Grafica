package pacotes.controle;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import pacotes.modelo.Scanline;

public class ControlePreenchimento {
	// DECLARACAO DAS VARIAVEIS

	private ArrayList<Scanline> scanlines = new ArrayList<Scanline>(); // ARRAY
																		// COM
																		// AS
																		// SCANLINES

	private double minY, maxY; // VARIAVEIS QUE INDICAM O PONTO Y MAIS BAIXO E
								// MAIS ALTO DA SCANLINE

	// *************************************************************************************
	// METODO QUE PREENCHE A FIGURA
	public void preencherFigura(Graphics g, ArrayList<Point> pontos) {

		// GERANDO AS SCANLINES
		for (int i = 1; i < pontos.size(); i++) {
			scanlines.add(new Scanline(pontos.get(i - 1).x, pontos.get(i).x, pontos.get(i - 1).y, pontos.get(i).y));
		}

		// CALCULANDO O PONTO Y MAIS BAIXO E MAIS ALTO DA SCANLINE
		minY = pontos.get(0).getY();
		maxY = pontos.get(0).getY();
		for (int i = 0; i < pontos.size(); i++) {
			double temp = pontos.get(i).y;
			if (temp < minY)
				minY = temp;
			else if (temp > maxY)
				maxY = temp;
		}

		// PREENCHER FIGURA
		for (int y = (int) minY; y < maxY; y++) {
			ArrayList<Integer> meetPoint = getPontosInterseccao(y);
			for (int i = 1; i < meetPoint.size(); i += 2) {
				g.drawLine(meetPoint.get(i), (int) y, meetPoint.get(i - 1), (int) y);
			}
		}
	}

	// *************************************************************************************
	// METODO QUE RECUPERA OS PONTOS DE INTERSECCAO
	// RETORNO -> ARRAY COM OS PONTOS DE INTERSECCAO
	public ArrayList<Integer> getPontosInterseccao(double y) {
		ArrayList<Integer> pontosInterseccao = new ArrayList<Integer>();

		for (int i = 0; i < scanlines.size(); i++) {
			Scanline scanL = scanlines.get(i);
			if (scanL.verificaInterseccao(y)) {
				pontosInterseccao.add(scanL.interseccaoX(y));
			}
		}

		// ORDENAR OS PONTOS DE INTERSECCAO
		Collections.sort(pontosInterseccao);

		return pontosInterseccao;
	}

}
