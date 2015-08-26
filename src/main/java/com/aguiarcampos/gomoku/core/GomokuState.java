package com.aguiarcampos.gomoku.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class GomokuState {

	/**
	 * indica tamanho do tabuleiro
	 */
	private static final int tamanhoTabuleiro = 15;

	/**
	 * check que representa o numero de sequecia mínima de peças seguidas para vencer o
	 * jogo
	 */
	private static final int check = 5;

	/**
	 * Representação do Jogador com a peça preta
	 */
	public static final String PRETA = "PRETA";
	
	/**
	 * Representação de nenhuma peça
	 */
	public static final String VAZIO = "VAZIO";
	
	/**
	 * Representação do Jogador com a peça branca
	 */
	public static final String BRANCA = "BRANCA";

	/**
	 * indicação do jogador da rodada atual
	 */
	private String jogadorAtual;

	/**
	 * variável booleana auxiliar para determinar vencedor
	 */
	private boolean vencedor = false;
	
	/**
	 * biblioteca google Table<Rows, Columns, Value> Table que associa posição X,Y do
	 * tabuleiro com a peça 
	 */
	private Table<Integer, Integer, String> posicaoPecas;
	

	public GomokuState() {
		//jogador inicial - padrão de início peças pretas
		this.jogadorAtual = PRETA;
		posicaoPecas = HashBasedTable.create();
		
		//inicialização da table com todas as casas vazias
		for (int i = 0; i < tamanhoTabuleiro; i++) {
			for (int j = 0; j < tamanhoTabuleiro; j++) {
				posicaoPecas.put(i, j, VAZIO);
			}
		}
	}

	/**
	 * Retorna o valor da casa dado as coordenadas de linha e coluna
	 * @param linha
	 * @param coluna
	 * @return
	 */
	public String getValorCasa(int linha, int coluna) {
		return posicaoPecas.get(linha, coluna);
	}

	/**
	 * Retorna próximo jogador
	 * 
	 * @return
	 */
	public String getJogadorAtual() {
		return jogadorAtual;
	}
	/**
	 * Se vencedor = True - retorna jogador vencedor senão retorna vazio
	 * @return
	 */
	public String getVencedor() {
		if (vencedor) {
			return jogadorAtual;
		}
		return VAZIO;
	}

	/**
	 * Realiza jogada settando os valores
	 * @param linha
	 * @param coluna
	 * @return
	 */
	public boolean realizarJogada(int linha, int coluna) {
		//colaca na Table a posição da jogada e o jogador
		this.posicaoPecas.put(linha, coluna, jogadorAtual);
		
		//verifica se na jogada atual houve um vencedor
		vencedor = verificarJogada(linha, coluna, jogadorAtual);
		
		//se não existir vencedor muda de jogador
		if (!vencedor) {
			jogadorAtual = jogadorAtual.equals(PRETA) ? BRANCA : PRETA;
		}
		return true;
	}

	/**
	 * verificar possibilidades de vitória da jogada nas 4 direções possíveis
	 * 
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return True se existir vencedor
	 */
	private boolean verificarJogada(int linha, int coluna, String jogador) {
		if (verificarLinha(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarColuna(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarDiagonalDireita(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarDiagonalEsquerda(linha, coluna, jogador) >= check) {
			return true;
		}
		return false;
	}

	/**
	 * verifica se existem o míinimo de 5 casas do mesmo jogador em linha 
	 * @param linhaFixa
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	private int verificarLinha(int linhaFixa, int coluna, String jogador) {
		int contador = 1;
		while (posicaoPecas.containsColumn(++coluna) && contador < 5) {
			if (posicaoPecas.get(linhaFixa, coluna).equals(jogador)) {
				contador++;
			}else
				break;
		}
		//retorna ao valor coluna ao seu ponto original
		coluna -= contador;
		while (posicaoPecas.containsColumn(--coluna) && contador < 5) {
			if (posicaoPecas.get(linhaFixa, coluna).equals(jogador)) {
				contador++;
			}else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o míinimo de 5 casas do mesmo jogador em coluna
	 * @param linha
	 * @param colunaFixa
	 * @param jogador
	 * @return
	 */
	private int verificarColuna(int linha, int colunaFixa, String jogador) {
		int contador = 1;

		while (posicaoPecas.containsRow(++linha) && contador < 5) {
			if (posicaoPecas.get(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor linha ao seu ponto original
		linha -= contador;
		while (posicaoPecas.containsRow(--linha) && contador < 5) {
			if (posicaoPecas.get(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o míinimo de 5 casas do mesmo jogador em diagonal ("\")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	private int verificarDiagonalDireita(int linha, int coluna, String jogador) {
		int contador = 1;

		while (posicaoPecas.containsRow(++linha)
				&& posicaoPecas.containsColumn(++coluna)  && contador < 5) {
			if (posicaoPecas.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna -= contador;
		while (posicaoPecas.containsRow(--linha)
				&& posicaoPecas.containsColumn(--coluna) && contador < 5) {
			if (posicaoPecas.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o míinimo de 5 casas do mesmo jogador em diagonal ("/")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	private int verificarDiagonalEsquerda(int linha, int coluna, String jogador) {
		int contador = 1;

		while (posicaoPecas.containsRow(++linha)
				&& posicaoPecas.containsColumn(--coluna) && contador < 5) {
			if (posicaoPecas.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna += contador;
		while (posicaoPecas.containsRow(--linha)
				&& posicaoPecas.containsColumn(++coluna) && contador < 5) {
			if (posicaoPecas.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

}
