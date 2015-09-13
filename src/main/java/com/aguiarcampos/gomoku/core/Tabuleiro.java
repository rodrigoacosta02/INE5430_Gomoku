package com.aguiarcampos.gomoku.core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TreeBasedTable;


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
	
	/**
	 * Representação de todas as proximas jogadas possívies
	 */
	@Getter
	private Set<Tabuleiro> possiveisJogadasTabuleiro;
	
	/**
	 * indicação do jogador da rodada atual
	 */
	@Getter
	protected String jogadorAtual;

	@Getter
	@Setter
	private int notaTabuleiro;
	
	@Setter
	private boolean fimJogo;
	
	@Getter
	@Setter
	int x = -1;
	@Getter
	@Setter
	int y = -1;
	public void jogada(){
		
	}
	
	/**
	 * Construtor padrao
	 */
	public Tabuleiro() {
		fimJogo = false;
		notaTabuleiro = 0;
		tabela = HashBasedTable.create();
		possiveisJogadasTabuleiro = new  HashSet<Tabuleiro>();
		possiveisJogadas = new HashSet<Point>();
	}

	/**
	 * Construtor com parametro Table
	 * @param tabela
	 */
	public Tabuleiro(Table<Integer, Integer, String> tabela) {
		this();
		this.tabela.putAll(tabela);
	}

	/**
	 * TODO rever se mantem mesmo endereco!!
	 * @param tabuleiro
	 */
	public Tabuleiro(Tabuleiro tabuleiro){
		this();
		copia(tabuleiro);
	}

	public boolean isFimJogo(){
		RegrasPontuacao rp = new RegrasPontuacao(this);
		return rp.verificaVencedor(this);
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
		if (!(jogador.equals(GomokuJogo.BRANCA) || jogador.equals(GomokuJogo.PRETA))) {
			throw new Exception("jogador inexistente");
		}
		
		if (this.tabela.contains(linha, coluna)) {
			throw new Exception("casa jah preenchida");
		}
		//colaca na Table a posição da jogada e o jogador
		this.tabela.put(linha, coluna, jogador);
		possiveisJogadas.remove(new Point(linha, coluna));
//		atualizarPontuacao();
		return false;
	}

	public void atualizarPontuacao() {
		// TODO Auto-generated method stub
		RegrasPontuacao rp = new RegrasPontuacao(this);
		rp.pontuacao();
		this.notaTabuleiro = rp.getPontuacao();
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
//		atualizarPossiveisJogadas();
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

	/**
	 * atualiza todas a variavel possiveisJogadas
	 */
	protected void atualizarPossiveisJogadas() {
		possiveisJogadas.removeAll(possiveisJogadas);
		int []linhaInicial = linha();
		int []colunaInicial = coluna();
		for (int linha = linhaInicial[0]; linha < linhaInicial[1]; linha++) {
			for (int coluna = colunaInicial[0]; coluna < colunaInicial[1]; coluna++) {
				if (!tabela.contains(linha, coluna)) {
					possiveisJogadas.add(new Point(linha, coluna));
				}
			}
		}
	}

	protected int[] linha(){
		int [] valor = new int[2];
		valor[0] = GomokuJogo.tamanhoTabuleiro;
		valor[1] = 0;
		for (Integer pointX : tabela.rowKeySet()) {
			if (pointX.intValue() < valor[0]) {
				valor[0] = pointX.intValue();
			}
			if (pointX.intValue() > valor[1]) {
				valor[1] = pointX.intValue();
			}
		}
		
		System.out.println(" -P " + valor[0]);
		valor[0] = ((valor[0] - 2) < 0 || valor[0] == GomokuJogo.tamanhoTabuleiro) ? 0: (valor[0] - 2);
		valor[1] = ((valor[1] + 2) > GomokuJogo.tamanhoTabuleiro || valor[1] == 0)? GomokuJogo.tamanhoTabuleiro : (valor[1] + 2);;
		
		return valor;
	}
	protected int[] coluna(){
		int [] valor = new int[2];
		valor[0] = GomokuJogo.tamanhoTabuleiro;
		valor[1] = 0;
		for (Integer pointY : tabela.columnKeySet()) {
			if (pointY.intValue() < valor[0]) {
				valor[0] = pointY.intValue();
			}
			if (pointY.intValue() > valor[1]) {
				valor[1] = pointY.intValue();
			}
		}
		valor[0] = ((valor[0] - 2) < 0 || valor[0] == GomokuJogo.tamanhoTabuleiro) ? 0: (valor[0] - 2);
		valor[1] = ((valor[1] + 2) > GomokuJogo.tamanhoTabuleiro || valor[1] == 0)? GomokuJogo.tamanhoTabuleiro : (valor[1] + 2);;
		
		return valor;
	}
	
	protected void atualizaProximaJogadaPossivel(String jogador) {
//		this.atualizarPossiveisJogadas();
		this.possiveisJogadas.removeAll(possiveisJogadas);
		this.possiveisJogadasTabuleiro.removeAll(possiveisJogadasTabuleiro);
		Table<Integer, Integer, String> aux = HashBasedTable.create(tabela);
		int []linhaInicial = linha();
		int []colunaInicial = coluna();

		for (int linha = linhaInicial[0]; linha < linhaInicial[1]; linha++) {
			for (int coluna = colunaInicial[0]; coluna < colunaInicial[1]; coluna++) {
				if (!tabela.contains(linha, coluna)) {
					possiveisJogadas.add(new Point(linha, coluna));
					aux.put(linha, coluna, jogador);
					Tabuleiro tab = new Tabuleiro(aux);
					tab.x = linha;
					tab.y = coluna;
					tab.jogadorAtual = jogador;
					this.possiveisJogadasTabuleiro.add(tab);
					aux.remove(linha, coluna);
				}
			}
		}
	}

	@Override
	public String toString() {
		String saida = "";
		for (Cell<Integer, Integer, String> casa: tabela.cellSet()) {
			saida +="("+casa.getRowKey() + ", " + casa.getColumnKey() + ") " + casa.getValue() + " | ";
		}
		saida += "Nota "+getNotaTabuleiro() + "\n#####";
		return saida ;
	}
	
}
