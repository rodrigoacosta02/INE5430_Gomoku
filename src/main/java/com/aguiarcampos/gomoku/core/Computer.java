package com.aguiarcampos.gomoku.core;

import lombok.Getter;
import lombok.Setter;

import com.aguiarcampos.gomoku.gui.GomokuPanel;

public class Computer extends IA_teste implements Runnable{
	private GomokuPanel panel;
	private boolean running;
	@Getter
	private Thread t;
	@Setter
	private boolean fimJogo;

	public Computer(GomokuPanel gomokuPanel) {
		super(GomokuJogo.PRETA);
		fimJogo = false;
		running = true;

		this.panel = gomokuPanel;
		t = new Thread(this);
	}
	
	public Computer(GomokuPanel gomokuPanel, String jogador) {
		super(jogador);
		fimJogo = false;
		running = true;
		this.panel = gomokuPanel;
		t = new Thread(this);
		t.start();
	}

	private void jogar() throws Exception {
		String jog = panel.state.getJogadorAtual();
		Tabuleiro mj = this.melhorPontuacao(3, panel.state.getTabuleiro(), jog);
		mj.jogadorAtual = jog;
		System.out.println("Jogo IA!");
		System.out.println(mj.getNotaTabuleiro());
		try {
			panel.iaJoga(mj);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void run() {
		while(!fimJogo){
			while(!running){
				Thread.yield();
			}
			try {
				jogar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    public void pauseThread() throws InterruptedException{
        running = false;
    }

    public void resumeThread(){
        running = true;
    }
}