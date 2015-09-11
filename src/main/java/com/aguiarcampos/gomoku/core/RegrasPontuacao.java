package com.aguiarcampos.gomoku.core;

import lombok.Getter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class RegrasPontuacao extends GomokuJogo {

	@Getter
	private int pontuacao = 0;

	public void pontuacaoLinha(Tabuleiro tabuleiro) {

		// percorre casas preenchidas
		for (Cell<Integer, Integer, String> casa : tabuleiro.tabela.cellSet()) {
			potuarLinha(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
		}

	}

	protected void potuarLinha(Integer linha, Integer coluna, String jogador) {
		// TODO Auto-generated method stub
		PontuacaoCasa pontuacaoCasa = new PontuacaoCasa(0, 1);
		int verificaColuna = coluna;
		// verifica a direita
		while ((++verificaColuna) < GomokuJogo.tamanhoTabuleiro
				&& pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
			if (getValorCasa(linha, coluna).equals(jogador)) {
				pontuacaoCasa.qntPecasConsecutiva++;
				pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
			} else if (getValorCasa(linha, coluna).equals(GomokuJogo.VAZIO)) {
				pontuacaoCasa.qntCasasLivres++;
				continue;
			} else{
				break;
			}
		}
		verificaColuna = coluna;

		// verifica a esquerda
		while ((--verificaColuna) > -1 && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
			if (getValorCasa(linha, coluna).equals(jogador)) {
				pontuacaoCasa.qntPecasConsecutiva++;
				pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
			} else if (getValorCasa(linha, coluna).equals(GomokuJogo.VAZIO)) {
				pontuacaoCasa.qntCasasLivres++;
				continue;
			} else
				break;
		}

		try {
			atualizaPontuacao(pontuacaoCasa, jogador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void atualizaPontuacao(PontuacaoCasa pontuacaoCasa, String jogador) throws Exception {
		// TODO Auto-generated method stub
		int alce = 0;
		
		if ((pontuacaoCasa.qntCasasLivres + pontuacaoCasa.qntPecasConsecutiva) < GomokuJogo.check) {
			throw new Exception (pontuacaoCasa.qntPecasConsecutiva+" sem chance de vencer por aqui " + pontuacaoCasa.qntCasasLivres); 
		}
		
		for (int i = 0; i < GomokuJogo.check*2; i++) {
			if (pontuacaoCasa.qntPecasConsecutiva > i) {
				alce += pontuacaoCasa.qntPecasConsecutiva * 10;
			} 
			if (pontuacaoCasa.qntCasasLivres > i) {
				alce += pontuacaoCasa.qntPecasConsecutiva + 50;
			}
			if(pontuacaoCasa.qntCasasLivres < i && pontuacaoCasa.qntPecasConsecutiva < i)
				break;
		}
		
		
		if (jogador.equals(GomokuJogo.PRETA)) {
			pontuacao += alce;
		} else
			pontuacao -= alce * 2;
	}
	
	void potuacaoPecasConsecutivas(PontuacaoCasa pontuacaoCasa, String jogador) throws Exception{
		
		if ((pontuacaoCasa.qntCasasLivres + pontuacaoCasa.qntPecasConsecutiva) < GomokuJogo.check) {
			throw new Exception (pontuacaoCasa.qntPecasConsecutiva+" sem chance de vencer por aqui " + pontuacaoCasa.qntCasasLivres); 
		}
		
		boolean vezIA = jogador.equals(IA_teste.pecaComptador);
		
		switch (pontuacaoCasa.qntPecasConsecutiva) {
			case 1:
				if (vezIA) {
					pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 1;
				} else{
					pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 10;					
				}
				
				break;
			case 2:
				if (vezIA) {
					pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 10;
				} else{
					pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 100;					
				}
				
				break;
			case 3:
				if (vezIA) {
					pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 100;
				} else{
					pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 1000;					
				}	
				
				break;
			case 4:
				if (vezIA) {
					pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 1000;
				} else{
					pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 10000;					
				}	
				
				break;
			case 5:
				if (vezIA) {
					pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao = Integer.MAX_VALUE;
				} else{
					pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao = Integer.MIN_VALUE;					
				}	
				break;

			default:
				break;
		}
		
	}
	 private int pontuacaoCasasLivresIA(int qntCasasLivre) {	
		 for (int i = 0; i < (GomokuJogo.check * 2); i++) {
			if (qntCasasLivre == i) {
				pontuacao += 10 * i;
			} else if (qntCasasLivre < i) {
				break;
			}
		}
		 return 0;
	}
	 
	 private int pontuacaoCasasLivresAdversario(int qntCasasLivre) {	
			if (qntCasasLivre == 1) {

			} else if (qntCasasLivre == 2) {
				
			} else if (qntCasasLivre == 3) {
				
			} else if (qntCasasLivre == 4) {
				
			} else if (qntCasasLivre == 5) {
				
			}		
		 return 0;
	}
	/**
	 * Estrutura de representacao da pontucao
	 */
	protected static class PontuacaoCasa {
		public int qntCasasLivres;
		public int qntPecasConsecutiva;
		public Table<Integer, Integer, String> posicaoPecas;

		
		public PontuacaoCasa(int qntCasasLivres, int qntPecasConsecutiva) {
			this.posicaoPecas = HashBasedTable.create();
			this.qntCasasLivres = qntCasasLivres;
			this.qntPecasConsecutiva = qntPecasConsecutiva;
		}
	}
}
