package com.aguiarcampos.gomoku.core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;


public class Tabuleiro {

	/**
	 * biblioteca google guava - Table<Linha, Coluna, Valor> Table que associa posição X,Y do
	 * tabuleiro com a peça 
	 */
	public Table<Integer, Integer, String> tabela;

	/**
	 * lista com as coordenadas das possiveis jogados do tabuleiro atual
	 */
	@Getter
	private Set<Point> possiveisJogadas;
	
	@Getter
	private Set<Tabuleiro> possiveisJogadasTabuleiro;
	
	@Getter
	@Setter
	private int notaTabuleiro;
	
	@Getter
	@Setter
	private boolean fimJogo;
	
	/**
	 * Construtor padrao
	 */
	public Tabuleiro() {
		fimJogo = false;
		notaTabuleiro = 0;
		tabela = HashBasedTable.create();
		possiveisJogadas = new HashSet<Point>();
		possiveisJogadasTabuleiro = new HashSet<Tabuleiro>();
		atualizarPossiveisJogadas();
	}

	/**
	 * Construtor com parametro Table
	 * @param tabela
	 */
	public Tabuleiro(Table<Integer, Integer, String> tabela) {
		this();
		this.tabela.putAll(tabela);
		atualizarPossiveisJogadas();
	}

	/**
	 * TODO rever se mantem mesmo endereco!!
	 * @param tabuleiro
	 */
	public Tabuleiro(Tabuleiro tabuleiro){
		this();
		copia(tabuleiro);
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
		if (jogador.equals(GomokuJogo.VAZIO)) {
			throw new Exception("jogador inexistente");
		}
		
		if (this.tabela.contains(linha, coluna)) {
			throw new Exception("casa jah preenchida");
		}
		//colaca na Table a posição da jogada e o jogador
		this.tabela.put(linha, coluna, jogador);
		atualizarPossiveisJogadas();
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
		if (!this.tabela.contains(linha, coluna)) {
			return GomokuJogo.VAZIO;
		}
		
		return tabela.get(linha, coluna);
	}

	/**
	 * Copia todo tabuleiro que foi passado como parametro.
	 * @param tab
	 */
	public void copia(Tabuleiro tab) {
		this.tabela.putAll(tab.tabela); //TODO ver se metodo esta correto
		atualizarPossiveisJogadas();
	}

	/**
	 * Torna a casa(linha, coluna) vazia
	 * @param linha
	 * @param coluna
	 */
	public void limpaCasa(int linha, int coluna) {
		this.tabela.remove(linha, coluna);
		this.possiveisJogadas.add(new Point(linha, coluna));
	}

	private Set<Tabuleiro> remove(Set<Point> listaPossocoes) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * atualiza todas a variavel possiveisJogadas
	 */
	private void atualizarPossiveisJogadas() {
		possiveisJogadas = new HashSet<Point>();
		for (int linha = 0; linha < GomokuJogo.tamanhoTabuleiro; linha++) {
			for (int coluna = 0; coluna < GomokuJogo.tamanhoTabuleiro; coluna++) {
				if (!tabela.contains(linha, coluna)) {
					possiveisJogadas.add(new Point(linha, coluna));
				}
			}
		}
	}
	

	/**
	 * atualiza todas a variavel possiveisJogadas
	 */
	private void atualizarPossiveisJogadasNoTabuleiro() {
		for (Point point : possiveisJogadas) {
			
		}
	}
	@Override
	public String toString() {
		String saida = "";
		for (Cell<Integer, Integer, String> casa: tabela.cellSet()) {
			saida += casa.getRowKey() + ", " + casa.getColumnKey() + " " + casa.getValue() + " | ";
			if (casa.getColumnKey() == GomokuJogo.tamanhoTabuleiro -1) {
				saida += "\n";
			}
		}
		return saida ;
	}
	
}
