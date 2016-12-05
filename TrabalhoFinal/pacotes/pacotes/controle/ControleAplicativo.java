package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import pacotes.view.MontarPainelInicial;

public class ControleAplicativo implements ActionListener, Runnable {

	// DECLARACAO DAS VARIAVEIS

	private MontarPainelInicial pnCenario; // VARIAVEL PARA A CLASSE
											// MontarPainelInicial

	private Graphics g; // VARIAVEL DO TIPO GRAPHICS

	// VARIAVEIS PARA AS CLASSES CONTROLES
	public ControleAnimacao controleAnimacao = new ControleAnimacao();
	private ControleBezier controleBezier = new ControleBezier();
	private ControleHermite controleHermite = new ControleHermite();
	private ControleTransformacao controleTransformacoes = new ControleTransformacao();

	// VARIAVEIS BOOLEANAS DE CONTROLE
	public boolean bezier = false;
	public boolean hermite = false;
	public boolean animacao1 = false;
	public boolean animacao2 = false;
	public boolean transformacoesUsuario = false;
	public boolean preenchimento = false;

	// ARRAYS COM OS PONTOS DAS FIGURAS
	public ArrayList<Point> pontosBezier = new ArrayList<>();
	public ArrayList<Point> pontosHermite = new ArrayList<>();
	private ArrayList<Point> pontosTransformacoesUsuario = new ArrayList<>();

	public double escala = 1.0; // FATOR DE ESCALA INICIAL

	public int h = -5; // FATOR DE TRANSLACAO HORIZONTAL INICIAL
	public int v = -5; // FATOR DE TRANSLACAO VERTICAL INICIAL
	public int direcaoTransformacao; // INDICA A DIRECAO DA TRANSFORMACAO
										// baixo = 1
										// direita = 2
										// cima = 3
										// esquerda = 4

	// VARIAVEIS PARA A CRIACAO E EXECUCAO DA THREAD
	protected Thread threadAnimacao;
	protected volatile boolean runningAnimacao = true;

	// ****************************************************************************
	// CONSTRUTOR DA CLASSE ControlarAplicativo
	public ControleAplicativo() {
		pnCenario = new MontarPainelInicial(this);
		pnCenario.showPanel();
		g = pnCenario.iniciarGraphics();
		pnCenario.btPararAnimacao.setEnabled(false);
	}

	// ****************************************************************************
	// ACTION PERFORMED - CAPTURAR E TRATAR CLIQUE DOS BOTÕES
	public void actionPerformed(ActionEvent e) {
		String comando;
		comando = e.getActionCommand();

		// BEZIER
		if (comando.equals("bezier")) {
			desenhaBezier(false);
		}

		// BEZIER ANIMADO
		if (comando.equals("bezieranimado")) {
			desenhaBezier(true);
		}

		// HERMITE
		if (comando.equals("hermite")) {
			desenhaHermite(false);
		}

		// HERMITE ANIMADO
		if (comando.equals("hermiteAnimado")) {
			desenhaHermite(true);
		}

		// PREENCHER A FIGURA
		if (comando.equals("preencher")) {
			if (!animacao1 && !animacao2 && !transformacoesUsuario) {
				if (pontosBezier != null || pontosHermite != null) {
					setarLinha(true);
					preenchimento = true;
					if (bezier) {
						new ControlePreenchimento().preencherFigura(g, pontosBezier);
					} else {
						new ControlePreenchimento().preencherFigura(g, pontosHermite);
					}
				}
			} else if (animacao1 || animacao2 || transformacoesUsuario) {
				preenchimento = true;
			}

		}

		// LIMPAR O PREENCHIMENTO DA FIGURA
		if (comando.equals("limparpreencher")) {
			if (preenchimento) {
				if (!animacao1 && !animacao2 && !transformacoesUsuario) {
					if (pontosBezier != null || pontosHermite != null) {
						preenchimento = false;
						if (bezier) {
							desenhaBezier(false);
						} else {
							desenhaHermite(false);
						}
					}
				} else if (animacao1 || animacao2 || transformacoesUsuario) {
					preenchimento = false;
				}
			}
		}

		// ANIMAÇÃO 1
		if (comando.equals("animacao1")) {
			if (pontosBezier != null || pontosHermite != null) {
				pnCenario.estadoBotoes(false);
				preenchimento = true;
				animacao1 = true;
				animacao();
			}
		}

		// ANIMAÇÃO 2
		if (comando.equals("animacao2")) {
			if (pontosBezier != null || pontosHermite != null) {
				pnCenario.estadoBotoes(false);
				preenchimento = true;
				animacao2 = true;
				animacao();
			}
		}

		// ZOOM IN
		if (comando.equals("zoomin")) {
			transformacoesUsuario = true;
			bezier = false;
			hermite = false;

			if (pontosBezier != null || pontosHermite != null || pontosTransformacoesUsuario != null) {

				if (escala <= 1.4) {
					escala += 0.1;
				}
				zoom();
			}
		}

		// ZOOM OUT
		if (comando.equals("zoomout")) {
			transformacoesUsuario = true;
			bezier = false;
			hermite = false;

			if (pontosBezier != null || pontosHermite != null || pontosTransformacoesUsuario != null) {
				if (escala >= 0.5) {
					escala -= 0.1;
				}
				zoom();
			}
		}

		// LIMPAR A TELA
		if (comando.equals("limpar")) {
			setarLinha(true);
			pontosBezier.clear();
			pontosHermite.clear();
		}

		// PARAR ANIMACAO
		if (comando.equals("pararanimacao")) {
			try {
				this.pararThread();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			pnCenario.estadoBotoes(true);
			animacao1 = false;
			animacao2 = false;
			pnCenario.btPararAnimacao.setEnabled(false);
		}

		// SAIR DA APLICAÇÃO
		if (comando.equals("sair")) {
			if (animacao1 || animacao2) {
				try {
					pararThread();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			System.exit(0);
		}
	}

	// ****************************************************************************
	// METODO QUE DEFINE SE OS PONTOS DA FIGURA SERAO INSERIDOS, SE TERA
	// ANIMACAO E DESENHA A FIGURA USANDO BEZIER
	private void desenhaBezier(boolean animacao) {
		bezier = true;
		hermite = false;
		g = pnCenario.iniciarGraphics();
		setarLinha(true);
		escala = 1.0;

		if (pontosBezier.size() == 0) {
			if (!animacao) {
				controleBezier.bezier(g, controleAnimacao, true, false, this);
			} else {
				controleBezier.bezier(g, controleAnimacao, true, true, this);
			}
		} else {
			if (!animacao) {
				controleBezier.bezier(g, controleAnimacao, false, false, this);
			} else {
				controleBezier.bezier(g, controleAnimacao, false, true, this);
			}
		}
		pontosBezier = controleBezier.getArrayDesenhoBezier();

		pontosTransformacoesUsuario = new ArrayList<Point>(pontosBezier.size());
		for (Point o : pontosBezier) {
			pontosTransformacoesUsuario.add((Point) o.clone());
		}

	}

	// ****************************************************************************
	// METODO QUE DEFINE SE OS PONTOS DA FIGURA SERAO INSERIDOS, SE TERA
	// ANIMACAO E DESENHA A FIGURA USANDO HERMITE
	private void desenhaHermite(boolean animacao) {
		bezier = false;
		hermite = true;
		escala = 1.0;
		g = pnCenario.iniciarGraphics();
		setarLinha(true);

		if (pontosHermite.size() == 0) {
			if (!animacao) {
				controleHermite.hermite(g, false, true, this, controleAnimacao);
			} else {
				controleHermite.hermite(g, true, true, this, controleAnimacao);
			}
		} else {
			if (!animacao) {
				controleHermite.hermite(g, false, false, this, controleAnimacao);
			} else {
				controleHermite.hermite(g, true, false, this, controleAnimacao);
			}
		}
		pontosHermite = controleHermite.getArrayDesenhoHermite();

		pontosTransformacoesUsuario = new ArrayList<Point>(pontosHermite.size());
		for (Point o : pontosHermite) {
			pontosTransformacoesUsuario.add((Point) o.clone());
		}
	}

	// ****************************************************************************
	// METODO QUE INICIA A THREAD PARA ANIMACAO
	private void animacao() {
		g = pnCenario.iniciarGraphics();
		pnCenario.btPararAnimacao.setEnabled(true);
		setarLinha(true);
		this.iniciarThread();
	}

	// ****************************************************************************
	// METODO UTILIZADO PARA O ZOOM NA IMAGEM
	public void zoom() {
		setarLinha(true);
		controleTransformacoes.escala(escala, pontosTransformacoesUsuario, g, true, true, true, controleAnimacao);
	}

	// ****************************************************************************
	// METODO USADO PARA AS TRANSLACOES QUE O USUARIO PODE EFETUAR POR MEIO DA
	// SETA DO TECLADO
	public void deslocar(int direcao) {
		setarLinha(true);
		pnCenario.estadoBotoes(false);

		transformacoesUsuario = true;
		bezier = false;
		hermite = false;
		animacao1 = false;
		animacao2 = false;
		preenchimento = true;
		pnCenario.btPararAnimacao.setEnabled(true);

		this.direcaoTransformacao = direcao;

		iniciarThread();
	}

	// ****************************************************************************
	// METODO QUE IRA SETAR A COR DA BORDA COMO AMARELO E LIMPARA OU NAO A TELA
	// PARAMETRO
	// -> ocultar: se true ira limpar a tela
	public void setarLinha(boolean ocultar) {
		if (ocultar) {
			pnCenario.ocultarDesenho();
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.YELLOW);
		}
	}

	// ****************************************************************************
	// METODO QUE IRA PARAR A EXECUCAO DA THREAD
	public void pararThread() throws InterruptedException {
		if (threadAnimacao != null) {
			runningAnimacao = false;
			threadAnimacao.join();
		}
	}

	// ****************************************************************************
	// METODO QUE INICIA THREAD
	public void iniciarThread() {
		if (!runningAnimacao)
			runningAnimacao = !runningAnimacao;

		threadAnimacao = new Thread(this);
		threadAnimacao.start();
	}

	// ****************************************************************************
	// METODO QUE DETERMINA O QUE SERA FEITO NA THREAD CRIADA
	@Override
	public void run() {
		if (animacao1) {
			if (bezier) {
				controleAnimacao.animacao1(pontosBezier, this, g, bezier);
			} else if (hermite) {
				controleAnimacao.animacao1(pontosHermite, this, g, bezier);
			} else if (transformacoesUsuario) {
				controleAnimacao.animacao1(pontosTransformacoesUsuario, this, g, bezier);
			}
		} else if (animacao2) {
			if (bezier) {
				controleAnimacao.animacao2(pontosBezier, this, g);
			} else if (hermite) {
				controleAnimacao.animacao2(pontosHermite, this, g);
			} else if (transformacoesUsuario) {
				controleAnimacao.animacao2(pontosTransformacoesUsuario, this, g);
			}
		} else if (transformacoesUsuario) {
			if (direcaoTransformacao == 1) { // baixo
				h = 0;
				if (v < 0) {
					v *= -1;
				}
				v++;
			} else if (direcaoTransformacao == 2) { // direita
				if (h < 0) {
					h *= -1;
				}
				h++;
				v = 0;
			} else if (direcaoTransformacao == 3) {// cima
				h = 0;
				if (v > 0) {
					v *= -1;
				}
				v--;
			} else if (direcaoTransformacao == 4) { // esquerda
				if (h > 0) {
					h *= -1;
				}
				h--;
				v = 0;
			}

			deslocarAnimacao();
		}
	}

	// ****************************************************************************
	// METODO PARA A TRANSLACAO DA FIGURA POR APOS O USUARIO TER CLICADO NAS
	// SETAS
	private void deslocarAnimacao() {

		while (runningAnimacao) {
			setarLinha(true);

			controleTransformacoes.translacao(h, v, pontosTransformacoesUsuario, g, true, preenchimento,
					controleAnimacao);

			if (controleAnimacao.testaColisão() == 2 || controleAnimacao.testaColisão() == 4) {
				h = -h;
			}

			if (controleAnimacao.testaColisão() == 1 || controleAnimacao.testaColisão() == 3) {
				v = -v;
			}

			// Controle da thread
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
				runningAnimacao = false;
			}
		}
	}

}
