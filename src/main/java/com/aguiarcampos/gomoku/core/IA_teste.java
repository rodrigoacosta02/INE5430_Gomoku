package com.aguiarcampos.gomoku.core;

import java.awt.Point;
import java.util.Random;
import java.util.Set;

public class IA_teste {

	/**
	 * refenrencia de game
	 */
	private GomokuJogo gomokuJogo;
	
	/**
	 * Representaçao da peça do Computador
	 */
	public static String pecaComptador;

	/**
	 * Ponto(x,y) de jogada
	 */
	private Point jogada;


	public IA_teste() {
		super();
	}

	/**
	 * Contrutor
	 * @param gomokuJogo
	 * @param pecaComptador
	 */
	public IA_teste(GomokuJogo gomokuJogo, String pecaComptador) {
		this.gomokuJogo = gomokuJogo;
		this.pecaComptador = pecaComptador;
		this.jogada = new Point();
	}
	
	/**
	 * inicia com a primeiro valor do primeiro movimento
	 */
	private void primeiraJogada(){
			//reduz a area de jogada para poder criar 5 em linha em todas as direcoes TODO
			int max = GomokuJogo.tamanhoTabuleiro - GomokuJogo.check;
			int min = GomokuJogo.check;
			Random random = new Random();
			jogada.x = random.nextInt((max-min) + 1 ) + min;
			jogada.y = random.nextInt((max-min) + 1 ) + min;
			System.out.println(jogada.x + " - " + jogada.y);
	}
	
	
	//TODO resolver problema se profundidade maior que o maximo de proximas jogadas
	@SuppressWarnings("unused")
	public MelhorJogada miniM(int profundidade, Tabuleiro tabuleiro, String jogadorAtual) throws Exception {
		//TODO validacao de vitoria 
		if (tabuleiro.getPossiveisJogadas().isEmpty()) {// vencedor do board atual
			return melhorPontuacao(tabuleiro);// vencedor
		}
		
		//define se jogada eh MAX ou MIN
		boolean jogadaMax = (jogadorAtual == GomokuJogo.PRETA);
		
		//define proximo jogador da arvore
		String proxJog = jogadaMax ? GomokuJogo.BRANCA : GomokuJogo.PRETA;

		//cria variaveis de retorno para decidir melhor jogada
		int melhorValor = jogadaMax ? Integer.MIN_VALUE : Integer.MAX_VALUE  ;
		Point melhorPosicaoPeca = null; 
		MelhorJogada melhorJogada = new MelhorJogada(melhorPosicaoPeca, melhorValor);

		//debug aux
		int a = 0;
		
		//verificacao de profundidade maxima da arvore
		if (profundidade > 0) {
			//criado novo tabuleiro sendo filho do atual
			Tabuleiro novoT = new Tabuleiro();
			novoT.copia(tabuleiro);
			
			//lista de possiveis jogadas do tabuleiro atual
			Set<Point> p = tabuleiro.getPossiveisJogadas();
			if (jogadaMax) {//MAX
				
				//percorre todos os pontos de possiveis jogadas do tabuleiro atual
				for (Point point : p) {
					
					//move peca para uma posicao vazia
					novoT.moverPeca(point.x, point.y, proxJog);
					
					//recursao em profundidade - retornando melhorJogada
					MelhorJogada novaJogada = miniM(profundidade - 1, novoT, proxJog);

					
					//verificação de melhor jogada MAX, se NÃO for maior atualiza melhor jogada
					if (!melhorJogada.potuacaoAtualMaiorQueNovaJogada(novaJogada)) {
						melhorJogada = novaJogada;
					}
					
					//volta jogada
					novoT.limpaCasa(point.x, point.y);
					
					
					//debug
//					if (profundidade==1) {
//						System.out.println(a++);
//						System.out.println("########## PRETO - " + profundidade);
//						System.out.println(melhorJogada.pontuacao + " -< melhorValor");
//						System.out.println(point.x +", " + point.y +" = jogadas");
//					}
				}

			} else {//MIN

				for (Point point : p) {

					novoT.moverPeca(point.x, point.y, proxJog);

					MelhorJogada novaJogada = miniM(profundidade - 1, novoT, proxJog);
					
					//verificação de melhor jogada MIN , se maior atualiza melhor jogada
					if (melhorJogada.potuacaoAtualMaiorQueNovaJogada(novaJogada)) {
						melhorJogada = novaJogada;
					}
					
					novoT.limpaCasa(point.x, point.y);
					
//					//debug
//					if (profundidade==1) {
//						System.out.println(a++);
//						System.out.println("########## BRANCO - " + profundidade);
//						System.out.println(melhorJogada.pontuacao + " -< melhorValor");
//						System.out.println(point.x +", " + point.y +" = jogadas");
//					}
				}
			}
			return melhorJogada;
			
		} else {
			return melhorPontuacao(tabuleiro);
		}
	}
	
	/**
	 * Metodo que definirá melhor pontuacao para um tabuleiro TODO 
	 * @param tabuleiro
	 * @return
	 */
	private MelhorJogada melhorPontuacao(Tabuleiro tabuleiro) {
		// TODO Auto-generated method stub
		RegrasPontuacao rp = new RegrasPontuacao();
		rp.pontuacaoLinha(tabuleiro);
		
		return new MelhorJogada(tabuleiro.getPossiveisJogadas().iterator().next(), rp.getPontuacao());
	}

	
	/*####################*/
	
	/**
	 * Estrutura de representacao da melhor jogada
	 */
	protected static class MelhorJogada {
		public Point jogada;
		public int pontuacao;

		public MelhorJogada(Point jogada, int pontuacao) {
			this.jogada = jogada;
			this.pontuacao = pontuacao;
		}
		
		public boolean potuacaoAtualMaiorQueNovaJogada(MelhorJogada novaJogada){
			return (pontuacao > novaJogada.pontuacao) ;
		}

	}

}
