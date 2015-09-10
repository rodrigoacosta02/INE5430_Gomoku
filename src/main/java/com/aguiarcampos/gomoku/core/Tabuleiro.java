package com.aguiarcampos.gomoku.core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;


public class Tabuleiro {

	/**
	 * biblioteca google guava - Table<Linha, Coluna, Valor> Table que associa posição X,Y do
	 * tabuleiro com a peça 
	 */
	protected Table<Integer, Integer, String> tabuleiro;

	/**
	 * lista com as coordenadas das possiveis jogados do tabuleiro atual
	 */
	@Getter
	private Set<Point> possiveisJogadas;
	
	
	/**
	 * Construtor padrao
	 */
	public Tabuleiro() {
		tabuleiro = HashBasedTable.create();
		possiveisJogadas = new HashSet<Point>(); 
		//inicialização da table com todas as casas vazias
		for (int i = 0; i < GomokuJogo.tamanhoTabuleiro; i++) {
			for (int j = 0; j < GomokuJogo.tamanhoTabuleiro; j++) {
				tabuleiro.put(i, j, GomokuJogo.VAZIO);
				possiveisJogadas.add(new Point(i, j));
			}
		}
	}

	/**
	 * Construtor com parametro Table
	 * @param tabuleiro
	 */
	public Tabuleiro(Table<Integer, Integer, String> tabuleiro) {
		this.tabuleiro = HashBasedTable.create();
		this.tabuleiro.putAll(tabuleiro);
		atualizarPossiveisJogadas();
	}
	
	/**
	 * Move uma peça para qualquer local do tabuleiro
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 * @throws Exception Casa do tabuleiro ja ocupada
	 */
	public boolean moverPeca(int linha, int coluna, String jogador) throws Exception {
		//validacao de peça existente na casa
		if (!this.tabuleiro.get(linha, coluna).equals(GomokuJogo.VAZIO)) {
			throw new Exception("Casa invalida - jah possui peca");
		}
		//colaca na Table a posição da jogada e o jogador
		this.tabuleiro.put(linha, coluna, jogador);
		possiveisJogadas.remove(new Point(linha, coluna));
		atualizarPontuacao();
		return false;
	}

	private void atualizarPontuacao() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Retorna o valor da casa dado as coordenadas de linha e coluna
	 * @param linha
	 * @param coluna
	 * @return
	 */
	public String getValorCasa(int linha, int coluna) {
		return tabuleiro.get(linha, coluna);
	}

	/**
	 * Copia todo tabuleiro que foi passado como parametro.
	 * @param tab
	 */
	public void copia(Tabuleiro tab) {
//		for (Cell<Integer, Integer, String> casa : tab.tabuleiro.cellSet()) {
//			this.tabuleiro.put(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
//		}
		this.tabuleiro.putAll(tab.tabuleiro); //TODO ver se metodo esta correto
		atualizarPossiveisJogadas();
	}

	/**
	 * Torna a casa(linha, coluna) vazia
	 * @param linha
	 * @param coluna
	 */
	public void limpaCasa(int linha, int coluna) {
		this.tabuleiro.put(linha, coluna, GomokuJogo.VAZIO);
	}


	/**
	 * atualiza todas a variavel possiveisJogadas
	 */
	private void atualizarPossiveisJogadas() {
		possiveisJogadas = new HashSet<Point>();
		for (Cell<Integer, Integer, String> casa : tabuleiro.cellSet()) {
			if (casa.getValue().equals(GomokuJogo.VAZIO)) {
				possiveisJogadas.add(new Point(casa.getRowKey(), casa.getColumnKey()));
			}
		}
	}
	
	@Override
	public String toString() {
		String saida = "";
		for (Cell<Integer, Integer, String> casa: tabuleiro.cellSet()) {
			saida += casa.getRowKey() + ", " + casa.getColumnKey() + " " + casa.getValue() + " | ";
			if (casa.getColumnKey() == GomokuJogo.tamanhoTabuleiro -1) {
				saida += "\n";
			}
		}
		return saida ;
	}
	
}
