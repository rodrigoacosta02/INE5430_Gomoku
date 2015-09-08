package pacoteTestes;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import com.aguiarcampos.gomoku.core.GomokuJogo;
import com.aguiarcampos.gomoku.core.NodoArvore;
import com.aguiarcampos.gomoku.core.Tabuleiro;
import com.google.common.collect.Table.Cell;

public class IA_teste {

	NodoArvore<Tabuleiro> tabuleiroRaiz;
	String iaPeca = GomokuJogo.PRETA;
	public IA_teste() {
		try {
			out = new PrintStream(new FileOutputStream("./teste"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabuleiroRaiz = new NodoArvore<Tabuleiro>();
	}

	public IA_teste(Tabuleiro tabuleiro) {
		this();
		this.tabuleiroRaiz = new NodoArvore<Tabuleiro>(tabuleiro);
	}
	PrintStream out ;

	private MelhorJogada minimax(String jogador, Tabuleiro tabuleiro) {
		boolean vezIA = jogador.equals(iaPeca);
		int melhorPontuacao = vezIA ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Point melhorJogada = null;
		Set<Point> possibilidades = new HashSet<Point>(tabuleiro.getPossiveisJogadas());
		
		for (Point movimento : possibilidades) {
			tabuleiro.moverPeca(movimento.x, movimento.y, jogador);
			String proximoJogador = jogador.equals(GomokuJogo.BRANCA) ? GomokuJogo.PRETA : GomokuJogo.BRANCA;
			MelhorJogada nova = minimax(proximoJogador, tabuleiro);
			if (vezIA) {
				if (nova.pontuacao > melhorPontuacao) {
					melhorJogada = movimento;
					melhorPontuacao = nova.pontuacao;
				}
			} else 
				if (nova.pontuacao < melhorPontuacao) {
					melhorJogada = movimento;
					melhorPontuacao = nova.pontuacao;
				}
			tabuleiro.limpaCasa(movimento.x, movimento.y);
		}
		return new MelhorJogada(melhorJogada, melhorPontuacao);
		
	}
	
	
	private void gerarFilhos(String jogador, Tabuleiro tabuleiro, int i) {
		//percorre todas casas tabuleiros
		for (Cell<Integer, Integer, String> casasTabu : tabuleiro.getTabuleiro().cellSet()) {
			
			//verifica se tabuleiro contem casas vazias
			if (tabuleiro.getTabuleiro().containsValue(GomokuJogo.VAZIO) && i < 11) {
				
				//realiza funcao somente nas casas vazias
				if (casasTabu.getValue().equals(GomokuJogo.VAZIO)) {
					
					//teste de nova jogada
					if (jogador.equals(GomokuJogo.PRETA)) {
						NodoArvore<Tabuleiro> nova = novaJogadaTabuleiro(GomokuJogo.PRETA, tabuleiro, casasTabu.getRowKey(), casasTabu.getColumnKey());
						gerarFilhos(GomokuJogo.BRANCA, nova.getNodoArvore(), i++);
					} else {
						NodoArvore<Tabuleiro> nova = novaJogadaTabuleiro(GomokuJogo.BRANCA, tabuleiro, casasTabu.getRowKey(), casasTabu.getColumnKey());
						gerarFilhos(GomokuJogo.PRETA, nova.getNodoArvore(), i++);
					}
					out.println(tabuleiro.toString());
					
					// tabuleiros.getFilhos().add(new
					// NodoArvore<Tabuleiro>(aux));
					// aux.getTabuleiro().put(casasTabu.getRowKey(),
					// casasTabu.getColumnKey(), GomokuJogo.VAZIO);
				}
			} else break;
		}
	}

	private NodoArvore<Tabuleiro> novaJogadaTabuleiro(String jogador, Tabuleiro tabuleiro, int linha, int coluna) {
		//cria novo tabuleiro para simulacao de nova jogada
		Tabuleiro tab = new Tabuleiro(); tab.copia(tabuleiro);
		NodoArvore<Tabuleiro> aux = new NodoArvore<Tabuleiro>(tab);
		tab.getTabuleiro().put(linha ,coluna, jogador);
		aux.getFilhos().add(new NodoArvore<Tabuleiro>(tab));
//		a.limpaCasa(linha ,coluna);
		return aux;
	}
	private int pontuacaoTabuleiro(Tabuleiro tabuleiro) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public String toString() {
//		String saida = "";
//		for (NodoArvore<Tabuleiro> nodo : tabuleiroRaiz) {
//			saida += nodo.getNodoArvore().toString();
//			saida += "\n";
//		}
//		return saida;
//	}
	private void tentativa() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		GomokuJogo gs = new GomokuJogo();
		IA_teste ia = new IA_teste(gs);
		ia.gerarFilhos(gs.getJogadorAtual(), gs, 0);
	
		System.out.println();
		System.out.println(ia.tabuleiroRaiz.numFilhos() + " num filhos");
		System.out
				.println("####################################################");

	}
	
	/**
	 * Representacao de melhor jogada 
	 */
	private static class MelhorJogada{
		public Point jogada;
		public int pontuacao;

		public MelhorJogada(Point jogada, int pontuacao) {
			this.jogada = jogada;
			this.pontuacao = pontuacao;
		}
		
	}
}
