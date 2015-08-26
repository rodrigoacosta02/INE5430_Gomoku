package com.aguiarcampos.gomoku.gui;

import javax.swing.JFrame;


public class Gomoku {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		
		//define tamanha da janela de exibição 
		frame.setSize(600, 650);
		
		//título da janela
		frame.setTitle("Gomoku - AI");
		
		//localização de abertura da janela
		frame.setLocation(10,10);
		
		// valor default ao fechar a janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GomokuPanel panel = new GomokuPanel(frame);
		
		//adiciona o painel ao Jframe
		frame.add(panel);
		
		//torna a janela visivel
		frame.setVisible(true);
	}
}
