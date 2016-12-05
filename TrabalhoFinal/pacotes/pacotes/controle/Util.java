package pacotes.controle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JButton;

public class Util {

	// *************************************************************************************
	// PLOTAR PONTO
	public void plotarPonto(Graphics g, int x, int y, boolean opcao) {
		if (opcao) {
			g.setColor(Color.RED);
		}
		g.drawLine(x, y, x, y);
	}

	// ******************************************************************************************************************
	// METODO UTILIZADO PARA ADICIONAR UM BOTAO A UM CONTAINER DO PROGRAMA
	// RETORNO -> BOTAO
	public JButton addAButton(String textoBotao, String textoControle, Container container, int tipoBotao, Icon icone) {
		JButton botao;

		if (tipoBotao == 0) { // BOTAO SO COM TEXTO
			botao = new JButton(textoBotao);
		} else { // BOTAO COM TEXTO E IMAGEM
			botao = new JButton(textoBotao, icone);
		}

		botao.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(botao);

		botao.setActionCommand(textoControle);

		return (botao);
	}

	// ******************************************************************************************************************
	// METODO USADO PARA DAR UMA PAUSA NA THREAD
	public void sleep(int velocidade, ControleAplicativo controlePrograma) {
		try {
			Thread.sleep(velocidade);
		} catch (InterruptedException e) {
			e.printStackTrace();
			controlePrograma.runningAnimacao = false;
		}
	}

}
