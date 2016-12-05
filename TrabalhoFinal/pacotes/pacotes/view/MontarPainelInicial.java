package pacotes.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pacotes.controle.ControleAplicativo;
import pacotes.controle.Util;

public class MontarPainelInicial implements MouseListener, MouseMotionListener {

	// DECLARACOES DAS VARIAVIES

	// FRAME PRINCIPAL
	public JFrame baseFrame;

	// PANELS UTILIZADOS
	private JPanel basePanel;
	public JPanel outputPanel;
	private JPanel buttonPanel;

	// BOTOES
	private JButton btBezier;
	private JButton btBezierAnimado;
	private JButton btHermite;
	private JButton btHermiteAnimado;
	private JButton btAnimacao1;
	private JButton btAnimacao2;
	private JButton btZoomin;
	private JButton btZoomout;
	private JButton btPreencher;
	private JButton btLimparPreencher;
	private JButton btLimpar;
	private JButton btSair;
	public JButton btPararAnimacao;

	// LABELS
	private JLabel labelVisor;
	public JLabel labelRodinhaMouse;

	// GRAPHICSG
	private Graphics desenho;

	// OBJETO PARA A CLASSE ControleAplicativo
	private ControleAplicativo controlePrograma;

	// ******************************************************************************************************************
	// MONTAR PAINEL INICIAL
	public MontarPainelInicial(ControleAplicativo controlePrograma) {
		this.controlePrograma = controlePrograma;
		Util u = new Util();

		// LAYOUT
		baseFrame = new JFrame();
		baseFrame.setLayout(new BoxLayout(baseFrame.getContentPane(), BoxLayout.Y_AXIS));
		baseFrame.setTitle("Trabalho Final CIC 270 - Gustavo Gomides - 30818");
		baseFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FITS PANEL TO THE
															// ACTUAL MONITOR

		// Adaptador para o fechamento da janela, matando o processo
		baseFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (controlePrograma.animacao1 || controlePrograma.animacao2) {
					try {
						controlePrograma.pararThread();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);

			}
		});

		baseFrame.setBackground(Color.BLACK);

		basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		basePanel.setBackground(Color.BLACK);

		// OUTPUT PANEL
		outputPanel = new JPanel();
		outputPanel.setLayout(new BorderLayout());
		outputPanel.setBackground(Color.BLACK);

		// BUTTON PANEL
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(0, 90));
		buttonPanel.setBackground(Color.BLUE);

		// LABEL DA VISUALIZACAO DO PONTO NA TELA
		JPanel panel = new JPanel(new BorderLayout());
		labelVisor = new JLabel("");
		labelVisor.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(labelVisor, BorderLayout.NORTH);
		labelVisor.setBackground(Color.WHITE);
		labelVisor.setForeground(Color.BLACK);
		labelVisor.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonPanel.add(panel);

		// BOTOES
		btBezier = u.addAButton("Bezier Batman", "bezier", buttonPanel, 0, null);
		btBezier.addActionListener(controlePrograma);
		btBezier.setBackground(Color.CYAN);
		btBezier.setForeground(Color.BLACK);

		btBezierAnimado = u.addAButton("Bezier Batman Animado", "bezieranimado", buttonPanel, 0, null);
		btBezierAnimado.addActionListener(controlePrograma);
		btBezierAnimado.setBackground(Color.CYAN);
		btBezierAnimado.setForeground(Color.BLACK);

		btHermite = u.addAButton("Hermite Batman", "hermite", buttonPanel, 0, null);
		btHermite.addActionListener(controlePrograma);
		btHermite.setBackground(Color.GREEN);
		btHermite.setForeground(Color.BLACK);

		btHermiteAnimado = u.addAButton("Hermite Batman BatmanAnimado", "hermiteAnimado", buttonPanel, 0, null);
		btHermiteAnimado.addActionListener(controlePrograma);
		btHermiteAnimado.setBackground(Color.GREEN);
		btHermiteAnimado.setForeground(Color.BLACK);

		btAnimacao1 = u.addAButton("Animação 1", "animacao1", buttonPanel, 0, null);
		btAnimacao1.addActionListener(controlePrograma);
		btAnimacao1.setBackground(Color.ORANGE);
		btAnimacao1.setForeground(Color.BLACK);

		btAnimacao2 = u.addAButton("Animação 2", "animacao2", buttonPanel, 0, null);
		btAnimacao2.addActionListener(controlePrograma);
		btAnimacao2.setBackground(Color.ORANGE);
		btAnimacao2.setForeground(Color.BLACK);

		btPararAnimacao = u.addAButton("Parar Animação", "pararanimacao", buttonPanel, 0, null);
		btPararAnimacao.addActionListener(controlePrograma);
		btPararAnimacao.setBackground(Color.GRAY);
		btPararAnimacao.setForeground(Color.BLACK);

		btPreencher = u.addAButton("Preencher", "preencher", buttonPanel, 0, null);
		btPreencher.addActionListener(controlePrograma);
		btPreencher.setBackground(Color.GRAY);
		btPreencher.setForeground(Color.BLACK);

		btLimparPreencher = u.addAButton("Limpar Preenchimento", "limparpreencher", buttonPanel, 0, null);
		btLimparPreencher.addActionListener(controlePrograma);
		btLimparPreencher.setBackground(Color.GRAY);
		btLimparPreencher.setForeground(Color.BLACK);

		Icon icon = new ImageIcon("pacotes\\pacotes\\imagens\\zoomin.png");
		btZoomin = u.addAButton("Zoom +", "zoomin", buttonPanel, 1, icon);
		btZoomin.addActionListener(controlePrograma);
		btZoomin.setBackground(Color.GRAY);
		btZoomin.setForeground(Color.BLACK);

		icon = new ImageIcon("pacotes\\pacotes\\imagens\\zoomout.png");
		btZoomout = u.addAButton("Zoom -", "zoomout", buttonPanel, 1, icon);
		btZoomout.addActionListener(controlePrograma);
		btZoomout.setBackground(Color.GRAY);
		btZoomout.setForeground(Color.BLACK);

		btLimpar = u.addAButton("Limpar", "limpar", buttonPanel, 0, null);
		btLimpar.addActionListener(controlePrograma);
		btLimpar.setBackground(Color.GRAY);
		btLimpar.setForeground(Color.BLACK);

		btSair = u.addAButton("Sair", "sair", buttonPanel, 0, null);
		btSair.addActionListener(controlePrograma);
		btSair.setBackground(Color.RED);
		btSair.setForeground(Color.BLACK);

		// LABEL INFORMATIVO
		String strLabel = "Use as setas do teclado para movimentar o desenho!";
		labelRodinhaMouse = new JLabel(strLabel);
		labelRodinhaMouse.setFont(new Font("Times", Font.BOLD, 12));
		labelRodinhaMouse.setForeground(Color.WHITE);
		labelRodinhaMouse.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonPanel.add(labelRodinhaMouse);

		// OUVINTES DO MOUSE
		outputPanel.addMouseListener(this);
		outputPanel.addMouseMotionListener(this);

		// OUVINTE DO TECLADO
		addKeyListener();

		// VISIBLE PANELS
		baseFrame.add(basePanel);
		basePanel.add(outputPanel, BorderLayout.CENTER);
		basePanel.add(buttonPanel, BorderLayout.PAGE_END);

		baseFrame.setVisible(true);
	}

	// ******************************************************************************************************************
	// METODO PARA MOSTRAR O FRAME BASICO
	public void showPanel() {
		basePanel.setVisible(true);
	}

	// ****************************************************************************************
	// METODO QUE CAPTURA A TECLA PRESSIONADA E CHAMA O METODO DE DESLOCAMENTO
	private void addKeyListener() {

		// SETA DIREITA
		baseFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "translacaoD");

		baseFrame.getRootPane().getActionMap().put("translacaoD", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlePrograma.pontosBezier.size() != 0 || controlePrograma.pontosHermite.size() != 0) {
					controlePrograma.setarLinha(true);
					estadoBotoes(false);
					controlePrograma.transformacoesUsuario = true;
					controlePrograma.bezier = false;
					controlePrograma.hermite = false;
					controlePrograma.deslocar(2);
				}
			}
		});

		// SETA ESQUERDA
		baseFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "translacaoE");

		baseFrame.getRootPane().getActionMap().put("translacaoE", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlePrograma.pontosBezier.size() != 0 || controlePrograma.pontosHermite.size() != 0) {
					controlePrograma.setarLinha(true);
					estadoBotoes(false);
					controlePrograma.transformacoesUsuario = true;
					controlePrograma.bezier = false;
					controlePrograma.hermite = false;
					controlePrograma.deslocar(4);
				}
			}
		});

		// SETA CIMA
		baseFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "translacaoC");

		baseFrame.getRootPane().getActionMap().put("translacaoC", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlePrograma.pontosBezier.size() != 0 || controlePrograma.pontosHermite.size() != 0) {
					controlePrograma.setarLinha(true);
					controlePrograma.transformacoesUsuario = true;
					controlePrograma.bezier = false;
					controlePrograma.hermite = false;
					controlePrograma.deslocar(3);
				}
			}
		});

		// SETA BAIXO
		baseFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "translacaoB");

		baseFrame.getRootPane().getActionMap().put("translacaoB", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlePrograma.pontosBezier.size() != 0 || controlePrograma.pontosHermite.size() != 0) {
					controlePrograma.setarLinha(true);
					controlePrograma.transformacoesUsuario = true;
					controlePrograma.bezier = false;
					controlePrograma.hermite = false;
					controlePrograma.deslocar(1);
				}
			}
		});
	}

	// ******************************************************************************************************************
	public void mouseClicked(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseEntered(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseMoved(MouseEvent e) {
		Point P = new Point(e.getX(), e.getY());
		this.labelVisor.setText("Ponto: ( " + (int) P.getX() + " ; " + (int) P.getY() + " )");
	}

	// ******************************************************************************************************************
	public void mouseExited(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mousePressed(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseReleased(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseDragged(MouseEvent e) {
	}

	// ******************************************************************************************************************
	// METODO QUE INICIA O COMPONENTE GRAFICO
	// RETORNO -> VARIAVEL DO TIPO GRAPHICS
	public Graphics iniciarGraphics() {
		desenho = outputPanel.getGraphics();
		return (desenho);
	}

	// ******************************************************************************************************************
	// OCULTAR O DESENHO
	public void ocultarDesenho() {
		desenho.clearRect(0, 0, outputPanel.getWidth(), outputPanel.getHeight());
		desenho.setColor(Color.BLACK);
	}

	// ****************************************************************************************
	// METODO 1 QUE HABILITA/DESABILITA OS BOTOES
	public void estadoBotoes(boolean estado) {
		btBezier.setEnabled(estado);
		btBezierAnimado.setEnabled(estado);
		btHermite.setEnabled(estado);
		btHermiteAnimado.setEnabled(estado);
		btAnimacao1.setEnabled(estado);
		btAnimacao2.setEnabled(estado);
		btZoomin.setEnabled(estado);
		btZoomout.setEnabled(estado);
		btLimpar.setEnabled(estado);
	}

	// ****************************************************************************************
	// METODO 2 QUE HABILITA/DESABILITA OS BOTOES
	public void estadoBotoes2(boolean estado) {
		btBezier.setEnabled(estado);
		btBezierAnimado.setEnabled(estado);
		btHermite.setEnabled(estado);
		btHermiteAnimado.setEnabled(estado);
		btAnimacao1.setEnabled(estado);
		btAnimacao2.setEnabled(estado);
		btZoomin.setEnabled(estado);
		btZoomout.setEnabled(estado);
		btPreencher.setEnabled(estado);
		btLimparPreencher.setEnabled(estado);
		btLimpar.setEnabled(estado);
	}

	// ******************************************************************************************************************

}
