package com.aguiarcampos.gomoku.core;

import java.awt.Point;
import java.util.HashSet;
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
	  private int alfa = Integer.MIN_VALUE;
	    private int beta = Integer.MAX_VALUE;


	public IA_teste() {
		this.jogada = new Point();
	}

	/**
	 * Contrutor
	 * @param gomokuJogo
	 * @param pecaComptador
	 */
	public IA_teste(GomokuJogo gomokuJogo, String pecaComptador) {
		this();
		this.gomokuJogo = gomokuJogo;
		this.pecaComptador = pecaComptador;
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
	public Tabuleiro miniM(int profundidade, Tabuleiro tabuleiro, String jogadorAtual) {
		//TODO nunca usado
		if (tabuleiro.isFimJogo()) {
			tabuleiro.atualizarPontuacao();
			return tabuleiro;// vencedor
		}
		
		//define se jogada eh MAX ou MIN
		boolean jogadaMax = (jogadorAtual == GomokuJogo.PRETA);
		
		//define proximo jogador da arvore
		String proxJog = jogadaMax ? GomokuJogo.BRANCA : GomokuJogo.PRETA;

		//cria variaveis de retorno para decidir melhor jogada
		int melhorValor = jogadaMax ? Integer.MIN_VALUE : Integer.MAX_VALUE  ;
		
		tabuleiro.setNotaTabuleiro(melhorValor);
		
		//verificacao de profundidade maxima da arvore
		if (profundidade > 0) {
			tabuleiro.atualizaProximaJogadaPossivel(jogadorAtual);
			//lista de possiveis jogadas do tabuleiro atual
			Set<Tabuleiro> listaTabs = new HashSet<Tabuleiro>(tabuleiro.getPossiveisJogadasTabuleiro());
			if (jogadaMax) {//MAX
				
				//percorre todos os pontos de possiveis jogadas do tabuleiro atual
				for (Tabuleiro novoT : listaTabs) {
					
					//recursao em profundidade - retornando melhorJogada
					Tabuleiro novaJogada = miniM(profundidade - 1, novoT, proxJog);
					
					if (novaJogada.getNotaTabuleiro() > tabuleiro.getNotaTabuleiro()) {
						tabuleiro.setNotaTabuleiro(novaJogada.getNotaTabuleiro());
					}

					if (tabuleiro.getNotaTabuleiro() > beta) {
						return tabuleiro;
					}
				}
				
				if (tabuleiro.getNotaTabuleiro() > alfa) {
					alfa = tabuleiro.getNotaTabuleiro();
				}

			} else {//MIN

				//percorre todos os pontos de possiveis jogadas do tabuleiro atual
				for (Tabuleiro novoT : listaTabs) {
					
					//recursao em profundidade - retornando melhorJogada
					Tabuleiro novaJogada = miniM(profundidade - 1, novoT, proxJog);
					
					if (novaJogada.getNotaTabuleiro() < tabuleiro.getNotaTabuleiro()) {
						tabuleiro.setNotaTabuleiro(novaJogada.getNotaTabuleiro());
					}

					if (tabuleiro.getNotaTabuleiro() < alfa) {
						return tabuleiro;
					}
				}
				
				if (tabuleiro.getNotaTabuleiro() < beta) {
					beta = tabuleiro.getNotaTabuleiro();
				}
			}
			return tabuleiro;
			
		} else {
			tabuleiro.atualizarPontuacao();
			return tabuleiro;// vencedor
		}
	}
	
	/**
	 * Metodo que definirá melhor pontuacao para um tabuleiro TODO 
	 * @param tabuleiro
	 * @return
	 */
	protected Tabuleiro melhorPontuacao(int profundidade, Tabuleiro tabuleiro, String jogador) {
		//define se jogada eh MAX ou MIN
		boolean jogadaMax = (jogador == GomokuJogo.PRETA);

		//cria variaveis de retorno para decidir melhor jogada
		Tabuleiro melhorValor = new Tabuleiro();
		
		melhorValor = miniM(profundidade, tabuleiro, jogador);
		
		Set<Tabuleiro> tabs = new HashSet<Tabuleiro>(tabuleiro.getPossiveisJogadasTabuleiro());

		for (Tabuleiro novoT : tabs) {
			if (melhorValor.getNotaTabuleiro() == novoT.getNotaTabuleiro()) {
				return novoT;
			}
		}
		
		return melhorValor;
	}
}
