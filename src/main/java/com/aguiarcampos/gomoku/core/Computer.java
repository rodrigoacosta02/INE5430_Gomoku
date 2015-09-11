package com.aguiarcampos.gomoku.core;

import com.aguiarcampos.gomoku.gui.GomokuPanel;

public class Computer extends IA_teste implements Runnable {
	private static final boolean aleatorio = false;
	GomokuPanel panel;
	public void run() {
		if (aleatorio) {
			jogoAleatorio();
		} else {
			try {
				IA_fraca();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void IA_fraca() throws Exception {
		// TODO Auto-generated method stub
		
		
		boolean pararJogada = false;
		while (!pararJogada) {
			String jog = panel.state.getJogadorAtual().equals(GomokuJogo.BRANCA)? GomokuJogo.PRETA : GomokuJogo.BRANCA;
			MelhorJogada mj = this.miniM(3, panel.state.getTabuleiro(), jog);
			
			System.out.println("Jogo IA!");
			try {
				pararJogada = panel.verificacaoJogada(mj.jogada.x, mj.jogada.y);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(mj.jogada.x + " - " + mj.jogada.y);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//			wait();
		}
	}

	static Thread t ;
	
//	public Computer(GomokuPanel gomokuPanel) {
//		//TODO somente inicializar uma vez ??
//		this.panel = gomokuPanel;
//		t = new Thread(this);
//		t.start();
//	}
	
	public Computer(GomokuPanel gomokuPanel) {
		//TODO somente inicializar uma vez ??
		this.panel = gomokuPanel;
//		t = new Thread(this);
//		t.start();
		try {
			jogar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void jogar() throws Exception {
		// TODO Auto-generated method stub
		String jog = panel.state.getJogadorAtual().equals(GomokuJogo.BRANCA)? GomokuJogo.PRETA : GomokuJogo.BRANCA;
		MelhorJogada mj = this.miniM(3, panel.state.getTabuleiro(), jog);
		
		System.out.println("Jogo IA!");
		System.out.println(mj.pontuacao + " - " + mj.jogada);
		try {
			panel.verificacaoJogada(mj.jogada.x, mj.jogada.y);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void pararJogo() throws InterruptedException{
		t.stop();
	}
	
	public void jogoAleatorio() {
		boolean pararJogada = false;
		while (!pararJogada) {
			
			System.out.println("Jogo aleatório!");
			int linha = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
			int coluna = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
			try {
				pararJogada = panel.verificacaoJogada(linha, coluna);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(linha + " - " + coluna);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}