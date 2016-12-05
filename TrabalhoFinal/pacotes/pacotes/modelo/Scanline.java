package pacotes.modelo;

public class Scanline {
	// DECLARACAO DAS VARIAVEIS
	double x1, x2, y1, y2; // COORDENADAS DOS PONTOS INICIAS E FINAIS DA
							// SCANLINE
	double m; // COEFICIENTE ANGULAR DA SCANLINE

	// ****************************************************************************************
	// CONSTRUTOR DA CLASSE SCANLINE
	public Scanline(double x1, double x2, double y1, double y2) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.m = (y2 - y1) / (x2 - x1);
	}

	// ****************************************************************************************
	// METODO QUE IRA CALCULAR O PONTO DE INTERSECCAO DA BORDA COM A SCANLINE
	// RETORNO -> PONTO DE INTERSECCAO
	public int interseccaoX(double y) {
		return (int) (((1 / m) * (y - y1)) + x1);
	}

	// ****************************************************************************************
	// METODO QUE IRA VERIFICAR SE A SCANLINE INTERCTA A BORDA
	// RETORNO = TRUE -> INTERSECTA
	// RETORNO = FALSE -> NAO INTERSECTA
	public boolean verificaInterseccao(double y) {
		if ((y >= y1 && y < y2) || (y >= y2 && y < y1)) {
			return true;
		}
		return false;
	}
	// ****************************************************************************************

}
