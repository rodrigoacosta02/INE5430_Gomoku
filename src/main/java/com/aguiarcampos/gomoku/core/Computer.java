package com.aguiarcampos.gomoku.core;

import com.aguiarcampos.gomoku.gui.GomokuPanel;

public class Computer implements Runnable {
	
	GomokuPanel panel;
	boolean jogando = false;
	public void run() {
		boolean pararJogada = false;
		while (!pararJogada) {
			
			System.out.println("Hello from a thread!");
			int linha = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
			int coluna = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
			pararJogada = panel.verificacaoJogada(linha, coluna);
			System.out.println(linha + " - " + coluna);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.jogando = false;
	}

	static Thread t ;
	
	public Computer(GomokuPanel gomokuPanel) {
		//TODO somente inicializar uma vez SINGLETON?
		if (!jogando) {
			this.panel = gomokuPanel;
			t = new Thread(this);
			t.start();
		}
		this.jogando = true;
	}
	
	public void pararJogo() throws InterruptedException{
		t.interrupted();
	}
	
}