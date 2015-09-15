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
	
	public int alfa;
	public int beta;
	
	@Getter
	@Setter
	int x = -1;
	@Getter
	@Setter
	int y = -1;
	
	/**
	 * Construtor padrao
	 */
	public Tabuleiro() {
		alfa = Integer.MIN_VALUE;
		beta = Integer.MAX_VALUE;
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
	 * Construtor 
	 * @param tabuleiro
	 */
	public Tabuleiro(Tabuleiro tabuleiro){
		this();
		copia(tabuleiro);
	}

	/**
	 * verificação se jogada representa o fim de jogo, caso verdadeiro atualiza nota do tabuleiro
	 * @return
	 */
	public boolean isFimJogo(){
		RegrasPontuacao rp = new RegrasPontuacao(this);
		boolean retorno = rp.verificaVencedor();
		if (retorno) {
			this.notaTabuleiro = rp.getPontuacao();
		}
		return retorno;
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
		return false;
	}

	/**
	 * Atualiza Pontuação do tabuleiro
	 */
	public void atualizarPontuacao() {
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
		this.tabela.putAll(tab.tabela);
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
	 * metodo que busca otimizar a busca em relacao a posicao das pecas do tabuleiro
	 * @return
	 */
	protected Set<Point> limitesBusca(){
		Set<Point> aux = new HashSet<Point>();
		for (Cell<Integer, Integer, String> casa : tabela.cellSet()) {
			
			if (casa.getRowKey() + 1 < GomokuJogo.tamanhoTabuleiro -1) {
				aux.add(new Point(casa.getRowKey() + 1 , casa.getColumnKey()));
				if (casa.getColumnKey() + 1 < GomokuJogo.tamanhoTabuleiro -1) {
					aux.add(new Point(casa.getRowKey() + 1 , casa.getColumnKey() +1));
				}
				if (casa.getColumnKey() - 1 > -1) {
					aux.add(new Point(casa.getRowKey() + 1 , casa.getColumnKey() -1));
				}
			}
			if (casa.getRowKey() - 1 > -1) {
				aux.add(new Point(casa.getRowKey() - 1 , casa.getColumnKey()));
				if (casa.getColumnKey() + 1 < GomokuJogo.tamanhoTabuleiro -1) {
					aux.add(new Point(casa.getRowKey() - 1 , casa.getColumnKey() +1));
				}
				if (casa.getColumnKey() - 1 > -1) {
					aux.add(new Point(casa.getRowKey() - 1 , casa.getColumnKey() -1));
				}
			}
			if (casa.getColumnKey() + 1 < GomokuJogo.tamanhoTabuleiro -1) {
				aux.add(new Point(casa.getRowKey(), casa.getColumnKey() + 1));
			}
			if (casa.getColumnKey() - 1 > -1) {
				aux.add(new Point(casa.getRowKey(), casa.getColumnKey() - 1));
			}
		}
		return aux;
	}
	
	/**
	 * Atualiza listas de possíveis jogadas  e possĩveis jogadas do tabuleiro
	 * @param jogador
	 */
	protected void atualizaProximaJogadaPossivel(String jogador) {
		this.possiveisJogadas.removeAll(possiveisJogadas);
		this.possiveisJogadasTabuleiro.removeAll(possiveisJogadasTabuleiro);
		Table<Integer, Integer, String> aux = HashBasedTable.create(tabela);

		for (Point p : limitesBusca()) {
			if (!tabela.contains(p.x, p.y)) {
				possiveisJogadas.add(p);
				aux.put(p.x, p.y, jogador);
				Tabuleiro tab = new Tabuleiro(aux);
				tab.x = p.x;
				tab.y = p.y;
				tab.jogadorAtual = jogador;
				this.possiveisJogadasTabuleiro.add(tab);
				aux.remove(p.x, p.y);
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
