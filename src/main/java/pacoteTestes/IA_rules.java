package pacoteTestes;

import java.util.Random;
import java.util.TreeSet;

import com.aguiarcampos.gomoku.core.GomokuJogo;
import com.aguiarcampos.gomoku.core.Tabuleiro;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TreeBasedTable;

public class IA_rules implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	private final int valorCasaAdversario = -10;
	private final int valorCasaJogador = 10;
	private final int valorSequenciaAdversario_2 = -300;
	private final int valorSequenciaAdversario_3 = -1000;
	private final int valorSequenciaAdversario_4 = -1500;
	private final int valorSequenciaAdversario_5 = -3000;
	private final int valorSequenciaJogador_2 = 100;
	private final int valorSequenciaJogador_3 = 300;
	private final int valorSequenciaJogador_4 = 400;
	private final int valorSequenciaJogador_5 = 3000;
	
	/**
	 * refenrencia de game
	 */
	private GomokuJogo gomokuJogo;
	
	/**
	 * Arvore de tabuleiros possiveis
	 * Table<linha, coluna, valor da casa>
	 */
	private Table<Integer, Integer, Integer> treeTable;
	
	/**
	 * Representaçao da peça do Computador
	 */
	private String pecaComptador;
	
	/**
	 * Represesnta maximo tamanho da arvore
	 */
	private int maxTamArvore = 4;

	private int coluna;

	private int linha;

	private boolean iniciaJogo;

	public IA_rules(GomokuJogo gomokuJogo, String pecaComptador) {
		this.gomokuJogo = gomokuJogo;
		this.pecaComptador = pecaComptador;
		this.treeTable = TreeBasedTable.create();
	}
	/**
	 * inicia com a primeito valor do primeiro movimento
	 */
	private void primeiraJogada(){
		if(this.iniciaJogo){
			this.linha = 7;
			this.coluna = 7;
		} else {
			//reduz a area de jogada para poder criar 5 em linha em todas as direcoes
			int max = 11;
			int min = 4;
			Random random = new Random();
			this.linha = random.nextInt((max-min) + 1 ) + min;
			this.coluna = random.nextInt((max-min) + 1 ) + min;
			System.out.println(linha + " - " + coluna);
		}
	}
	
	public static void main(String[] args) {
		IA_rules rules = new IA_rules(null, null);
		for (int i = 0; i < 10; i++) {
			rules.primeiraJogada();
		}
	}
	/**
	 * criar arvore de possiveis jogadas
	 */
	private void arvoreJogadas(){
		//TODO
	}
	
	/**
	 * Definicao de melhor jogada
	 */
	private void escolhaMehorJogada(){
		//TODO
	}
	
	/**
	 * analise de jogada do adversario
	 */
	private void verificacaoJogadaAdversario() {
		// TODO Auto-generated method stub

	}
	
	private boolean minimax(String jogador, Table<Integer, Integer, String> tabuleiro){
//		TODO
		Tabuleiro novo = new Tabuleiro(tabuleiro);
		if (!gomokuJogo.getVencedor().equals(GomokuJogo.VAZIO)) {
			return false;
		}
		
		for (Cell<Integer, Integer, String> entrada : novo.getTabuleiro().cellSet()) {
			if (entrada.equals(GomokuJogo.VAZIO)) {
				novo.getTabuleiro().put(entrada.getRowKey(), entrada.getColumnKey(), jogador);
				
				if (this.pecaComptador.equals(jogador)) {
					//TODO   max
				} else {
					//TODO   min
				}
				System.out.println( );
			}
		}
		return false;
/*		minimax(player,board)
	    if(game over in current board position)
	        return winner
	    children = all legal moves for player from this board
	    if(max's turn)
	        return maximal score of calling minimax on all the children
	    else (min's turn)
	        return minimal score of calling minimax on all the children
*/	}
	
	private void alfaBeta() {
		// TODO Auto-generated method stub
/*alpha-beta(player,board,alpha,beta)
    if(game over in current board position)
        return winner

    children = all legal moves for player from this board
    if(max's turn)
        for each child
            score = alpha-beta(other player,child,alpha,beta)
            if score > alpha then alpha = score (we have found a better best move)
            if alpha >= beta then return alpha (cut off)
        return alpha (this is our best move)
    else (min's turn)
        for each child
            score = alpha-beta(other player,child,alpha,beta)
            if score < beta then beta = score (opponent has found a better worse move)
            if alpha >= beta then return beta (cut off)
        return beta (this is the opponent's best move)

*/	}
}
