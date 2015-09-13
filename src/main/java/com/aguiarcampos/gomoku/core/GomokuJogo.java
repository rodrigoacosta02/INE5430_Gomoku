package com.aguiarcampos.gomoku.core;

import com.google.common.collect.Table.Cell;


public class GomokuJogo extends Tabuleiro{
	
	/**
	 * indica tamanho do tabuleiro
	 */
	public static final int tamanhoTabuleiro = 5;

	/**
	 * check que representa o numero de sequencia mínima de peças seguidas para vencer o
	 * jogo
	 */
	public static final int check = 3;

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
	 * variável booleana auxiliar para determinar vencedor
	 */
	private static boolean vencedor = false;

	/**
	 * Construtor padrao
	 */
	public GomokuJogo() {
		//inicializa a classe extendida
		super();
//		jogador inicial - padrão de início peças pretas
		this.jogadorAtual = PRETA;
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

	public Tabuleiro getTabuleiro(){
		return this;
	}
	
	/**
	 * Realiza jogada settando os valores
	 * @param linha
	 * @param coluna
	 * @return
	 * @throws Exception 
	 */
	public boolean realizarJogada(int linha, int coluna) throws Exception {
		moverPeca(linha, coluna, jogadorAtual);
		atualizaProximaJogadaPossivel(jogadorAtual);
		//verifica se na jogada atual houve um vencedor
		vencedor = verificarJogada(linha, coluna, jogadorAtual);
		this.setFimJogo(vencedor);
		
		//se não existir vencedor muda de jogador
		if (!vencedor) {
			jogadorAtual = jogadorAtual.equals(PRETA) ? BRANCA : PRETA;
		}
		
		return true;
	}
	
	public void realizarJogada(Tabuleiro tab){
		this.copia(tab);
		atualizaProximaJogadaPossivel(jogadorAtual);
		//verifica se na jogada atual houve um vencedor
		vencedor = verificarVencedor();
		this.setFimJogo(vencedor);
		
		//se não existir vencedor muda de jogador
		if (!vencedor) {
			jogadorAtual = jogadorAtual.equals(PRETA) ? BRANCA : PRETA;
		}
	}
	
	
	private boolean verificarVencedor() {
		for (Cell<Integer, Integer, String> casa : tabela.cellSet()) {
			if(verificarJogada(casa.getRowKey(), casa.getColumnKey(), casa.getValue()))
				return true;
		}
		return false;
	}

	/*	
	####################################################################
						vertificacoes de jogadas
	*/	
	
	/**
	 * verificar possibilidades de vitória da jogada nas 4 direções possíveis
	 * 
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return True se existir vencedor
	 */
	public boolean verificarJogada(int linha, int coluna, String jogador) {
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
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em linha 
	 * @param linhaFixa
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarLinha(int linhaFixa, int coluna, String jogador) {
		int contador = 1;
		while (tabela.containsColumn(++coluna) && contador < check) {
			if (getValorCasa(linhaFixa, coluna).equals(jogador)) {
				contador++;
			}else
				break;
		}
		//retorna ao valor coluna ao seu ponto original
		coluna -= contador;
		while (tabela.containsColumn(--coluna) && contador < check) {
			if (getValorCasa(linhaFixa, coluna).equals(jogador)) {
				contador++;
			}else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em coluna
	 * @param linha
	 * @param colunaFixa
	 * @param jogador
	 * @return
	 */
	public int verificarColuna(int linha, int colunaFixa, String jogador) {
		int contador = 1;

		while (tabela.containsRow(++linha) && contador < check) {
			if (getValorCasa(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor linha ao seu ponto original
		linha -= contador;
		while (tabela.containsRow(--linha) && contador < check) {
			if (getValorCasa(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em diagonal ("\")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarDiagonalDireita(int linha, int coluna, String jogador) {
		int contador = 1;

		while (tabela.containsRow(++linha)
				& tabela.containsColumn(++coluna)  && contador < check) {
			if (getValorCasa(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna -= contador;
		while (tabela.containsRow(--linha)
				&& tabela.containsColumn(--coluna) && contador < check) {
			if (getValorCasa(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em diagonal ("/")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarDiagonalEsquerda(int linha, int coluna, String jogador) {
		int contador = 1;

		while (tabela.containsRow(++linha)
				& tabela.containsColumn(--coluna) && contador < check) {
			if (getValorCasa(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna += contador;
		while (tabela.containsRow(--linha)
				&& tabela.containsColumn(++coluna) && contador < check) {
			if (getValorCasa(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}
}
