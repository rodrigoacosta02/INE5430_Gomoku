package com.aguiarcampos.gomoku.core;

import com.aguiarcampos.gomoku.gui.GomokuPanel;

public class Computer extends IA_teste {
	private GomokuPanel panel;
	
	public Computer(GomokuPanel gomokuPanel) {
		super();
		this.panel = gomokuPanel;
	}
	
	public void jogar() throws Exception {
		// TODO Auto-generated method stub
		String jog = panel.state.getJogadorAtual();
		Tabuleiro mj = this.melhorPontuacao(3, panel.state.getTabuleiro(), jog);
		mj.jogadorAtual = jog;
		System.out.println("Jogo IA!");
		System.out.println(mj.getNotaTabuleiro() + " - " );
		try {
			panel.iaJoga(mj);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}