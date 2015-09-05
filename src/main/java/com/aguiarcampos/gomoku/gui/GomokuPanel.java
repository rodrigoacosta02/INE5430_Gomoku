package com.aguiarcampos.gomoku.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.aguiarcampos.gomoku.core.Computer;
import com.aguiarcampos.gomoku.core.GomokuJogo;


/**
 * Classe que extend JPanel - definindo aspectos visual do Jogo GUI inspirado e
 * modificado a partir de :
 * http://cs.gettysburg.edu/~tneller/cs111/gomoku/gui/Gomoku.java
 *
 */
public class GomokuPanel extends JPanel implements ActionListener {
	private final int MARGIN = 5;
	private final double PIECE_FRAC = 0.9;
	private final String comandoReiniciar = "REINICIAR";
	private final String comandoInicioComp = "PC_JOGA";
	
	public GomokuJogo state;
	private JFrame frame;
	private Computer c;

	public GomokuPanel(JFrame frame) {
		super();
		this.frame = frame;
		state = new GomokuJogo();
		addMouseListener(new GomokuListener());
		JButton reiniciarPartida;
		reiniciarPartida = new JButton("Nova partida");
		reiniciarPartida.setVerticalTextPosition(AbstractButton.BOTTOM);
		reiniciarPartida.setHorizontalTextPosition(AbstractButton.CENTER);
		reiniciarPartida.setMnemonic(KeyEvent.VK_R);
		reiniciarPartida.setActionCommand(comandoReiniciar);
		
		JButton computadorIniciaPartida;
		computadorIniciaPartida = new JButton("Computador inicia partida");
		computadorIniciaPartida.setVerticalTextPosition(AbstractButton.BOTTOM);
		computadorIniciaPartida.setHorizontalTextPosition(AbstractButton.CENTER);
		computadorIniciaPartida.setActionCommand(comandoInicioComp);
		
		reiniciarPartida.addActionListener(this);
		computadorIniciaPartida.addActionListener(this);
		add(reiniciarPartida);
		add(computadorIniciaPartida);

	}

	public void actionPerformed(ActionEvent e) {
		if (comandoReiniciar.equals(e.getActionCommand())) {
			state = new GomokuJogo();
			addMouseListener(new GomokuListener());
			repaint();
			try {
				c.pararJogo();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (comandoInicioComp.equals(e.getActionCommand())) {
			//TODO pc inicia game
			System.out.println("I will beat you");
			c = new Computer(this);
		}
		

	}

	class GomokuListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			//
			double panelWidth = getWidth();
			double panelHeight = getHeight();

			double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
			double squareWidth = boardWidth / GomokuJogo.tamanhoTabuleiro;

			double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
			double yTop = (panelHeight - boardWidth) / 2 + MARGIN;

			int col = (int) Math.round((e.getX() - xLeft) / squareWidth - 0.5);
			int row = (int) Math.round((e.getY() - yTop) / squareWidth - 0.5);

			verificacaoJogada(row, col);
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		double panelWidth = getWidth();
		double panelHeight = getHeight();

		g2.setColor(new Color(0.925f, 0.670f, 0.34f)); // light wood
		g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));

		double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
		double squareWidth = boardWidth / GomokuJogo.tamanhoTabuleiro;
		double gridWidth = (GomokuJogo.tamanhoTabuleiro - 1) * squareWidth;
		double pieceDiameter = PIECE_FRAC * squareWidth;
		boardWidth -= pieceDiameter;
		double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
		double yTop = (panelHeight - boardWidth) / 2 + MARGIN;

		g2.setColor(Color.BLACK);
		for (int i = 0; i < GomokuJogo.tamanhoTabuleiro; i++) {
			double offset = i * squareWidth;
			g2.draw(new Line2D.Double(xLeft, yTop + offset, xLeft + gridWidth,
					yTop + offset));
			g2.draw(new Line2D.Double(xLeft + offset, yTop, xLeft + offset,
					yTop + gridWidth));
		}

		for (int row = 0; row < GomokuJogo.tamanhoTabuleiro; row++)
			for (int col = 0; col < GomokuJogo.tamanhoTabuleiro; col++) {
				String piece = state.getValorCasa(row, col);
				if (!piece.equals(GomokuJogo.VAZIO)) {
					Color c = (piece.equals(GomokuJogo.PRETA)) ? Color.BLACK
							: Color.WHITE;
					g2.setColor(c);

					double xCenter = xLeft + col * squareWidth;
					double yCenter = yTop + row * squareWidth;
					Ellipse2D.Double circle = new Ellipse2D.Double(xCenter
							- pieceDiameter / 2, yCenter - pieceDiameter / 2,
							pieceDiameter, pieceDiameter);
					g2.fill(circle);
					g2.setColor(Color.black);
					g2.draw(circle);
				}
			}
	}

	/**
	 * verificação se local do clique está dentro das delimitações do
	 * tabuleiro se o valor da casa que foi clicada está vazia e se já
	 * ocorreu um vencedor impedindo novas jogadas
	 * @param linha
	 * @param coluna
	 * @return false se jogo nao acabou
	 */
	public boolean verificacaoJogada(int linha, int coluna) {
		if (linha >= 0 && linha < GomokuJogo.tamanhoTabuleiro && coluna >= 0 && coluna < GomokuJogo.tamanhoTabuleiro
				&& state.getValorCasa(linha, coluna).equals(GomokuJogo.VAZIO)
				&& state.getVencedor().equals(GomokuJogo.VAZIO)) {
			state.realizarJogada(linha, coluna);
			repaint();
			String vencedor = state.getVencedor();
			// exibe uma msg de vitória na tela
			if (!vencedor.equals(GomokuJogo.VAZIO)){
				JOptionPane
						.showMessageDialog(
								frame,
								(vencedor.equals(GomokuJogo.PRETA)) ? "Vencedor pedras PRETAS!"
										: "Vencedor pedras BRANCAS!!");
				return true;
			}
		}
		return false;
	}

}