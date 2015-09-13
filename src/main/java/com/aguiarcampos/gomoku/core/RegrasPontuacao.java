package com.aguiarcampos.gomoku.core;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class RegrasPontuacao {

	@Getter
	private int pontuacao = 0;
	private Set<PontuacaoCasa> casasPontuadasEmLinha = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> casasPontuadasEmColuna = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> casasPontuadasEmDiagonalDireita = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> casasPontuadasEmDiagonalEsquerda = new HashSet<RegrasPontuacao.PontuacaoCasa>();

	private Tabuleiro tabuleiro;
	
	public RegrasPontuacao(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public boolean verificaVencedor(Tabuleiro tabuleiro){
		GomokuJogo gj = new GomokuJogo();
		gj.tabela.putAll(tabuleiro.tabela);
		for (Cell<Integer, Integer, String> casa : gj.tabela.cellSet()) {
			if (gj.verificarJogada(casa.getRowKey(), casa.getColumnKey(), casa.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * percorre tabuleiro 
	 * @param tabuleiro
	 */
	public void pontuacao() {

		// percorre casas preenchidas
		for (Cell<Integer, Integer, String> casa : tabuleiro.tabela.cellSet()) {
			casasPontuadasEmLinha.add(pontuarLinha(casa.getRowKey(), casa.getColumnKey(), casa.getValue()));
			casasPontuadasEmColuna.add(pontuarColuna(casa.getRowKey(), casa.getColumnKey(), casa.getValue()));
			casasPontuadasEmDiagonalDireita.add(pontuarDiagonalDireita(casa.getRowKey(), casa.getColumnKey(), casa.getValue()));
			casasPontuadasEmDiagonalEsquerda.add(pontuarDiagonalEsquerda(casa.getRowKey(), casa.getColumnKey(), casa.getValue()));
		}
		pontuar();
		System.out.println(pontuacao + " - " + tabuleiro.toString());
	}

	private void pontuar() {
		for (PontuacaoCasa pontuacaoCasa : casasPontuadasEmLinha) {
			try {
				pontuacaoPecasConsecutivas(pontuacaoCasa);
			} catch (Exception e) {
				System.err.println(e.getMessage() + " - linha\n");
			}
		}
		 for (PontuacaoCasa pontuacaoCasa : casasPontuadasEmColuna) {
			 try {
					pontuacaoPecasConsecutivas(pontuacaoCasa);
				} catch (Exception e) {
					System.err.println(e.getMessage()+ " - coluna\n");
				}
		}
		 for (PontuacaoCasa pontuacaoCasa : casasPontuadasEmDiagonalDireita) {
			 try {
					pontuacaoPecasConsecutivas(pontuacaoCasa);
				} catch (Exception e) {
					System.err.println(e.getMessage()+ " - diag \\\n");
				}
		}
		 for (PontuacaoCasa pontuacaoCasa : casasPontuadasEmDiagonalEsquerda) {
			 try {
					pontuacaoPecasConsecutivas(pontuacaoCasa);
				} catch (Exception e) {
					System.err.println(e.getMessage()+ " - diag /\n");
				}
		}
	}
	
	/**
	 * Pontucao 
	 * @param pontuacaoCasa
	 * @throws Exception
	 */
	public void pontuacaoPecasConsecutivas(PontuacaoCasa pontuacaoCasa) throws Exception{
		
		//caso seja uma direcao que não seja possível vencer é gerado uma exceção
		if ((pontuacaoCasa.qntCasasLivres + pontuacaoCasa.qntPecasConsecutiva) < GomokuJogo.check) {
			throw new Exception (pontuacaoCasa.qntPecasConsecutiva+" sem chance de vencer por aqui " + pontuacaoCasa.qntCasasLivres); 
		}
		
		//verifica se a pontuacao é MIN ou MAX
		boolean vezIA = pontuacaoCasa.posicaoPecas.containsValue(GomokuJogo.PRETA);
		
		switch (pontuacaoCasa.qntPecasConsecutiva) {
			case 1:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 1;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 10;					
				}
				
				break;
			case 2:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 100;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 300;					
				}
				
				break;
			case 3:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 200;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 1000;					
				}	
				
				break;
			case 4:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 400;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 1500;					
				}	
				
				break;
			case 5:
				if (vezIA) {
					pontuacao = Integer.MAX_VALUE;
				} else{
					pontuacao = Integer.MIN_VALUE;					
				}	
				break;

			default:
				break;
		}
	}

	 /**
	  * Pontucao de casas livres TODO 
	  * @param qntCasasLivre
	  * @return
	  */
	 protected int pontuacaoCasasLivresIA(int qntCasasLivre) {	
		 int retorno = 0;
		 for (int i = 1; i < (GomokuJogo.check * GomokuJogo.tamanhoTabuleiro); i++) {
			if (qntCasasLivre == i) {
				retorno += (5 * i);
			} else if (qntCasasLivre < i) {
				break;
			}
		}
		 return retorno;
	}
	 /**
	  * Pontucao de casas livres do adversario TODO 
	  * @param qntCasasLivre
	  * @return
	  */
	 protected int pontuacaoCasasLivresAdversario(int qntCasasLivre) {	
		 int retorno = 0;
		 for (int i = 1; i < (GomokuJogo.check * GomokuJogo.tamanhoTabuleiro); i++) {
			if (qntCasasLivre == i) {
				retorno -= (5 * i);
			} else if (qntCasasLivre < i) {
				break;
			}
		}
		 return retorno;
	}

	 /**
		 * A partir da posição da peça é percorrido a linha para verificar qntidade de peças consecutivas e casas vazias
		 * 
		 * @param linha
		 * @param coluna
		 * @param jogador
		 */
		protected PontuacaoCasa pontuarLinha(Integer linha, Integer coluna, String jogador) {

			PontuacaoCasa pontuacaoCasa = new PontuacaoCasa(0, 1);
			pontuacaoCasa.posicaoPecas.put(linha, coluna, jogador);
			int verificaColuna = coluna;
			
			// verifica a direita
			while ((++verificaColuna) < GomokuJogo.tamanhoTabuleiro
					&& pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(linha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(linha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else{
					break;
				}
			}
			verificaColuna = coluna;

			// verifica a esquerda
			while ((--verificaColuna) > -1 && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(linha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(linha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else
					break;
			}
			return pontuacaoCasa;
		}
		
		/**
		 * A partir da posição da peça é percorrido a coluna para verificar qntidade de peças consecutivas e casas vazias
		 * 
		 * @param linha
		 * @param coluna
		 * @param jogador
		 */
		protected PontuacaoCasa pontuarColuna(Integer linha, Integer coluna, String jogador) {

			PontuacaoCasa pontuacaoCasa = new PontuacaoCasa(0, 1);
			pontuacaoCasa.posicaoPecas.put(linha, coluna, jogador);
			int verificaLinha = linha;
			
			// verifica a direita
			while ((++verificaLinha) < GomokuJogo.tamanhoTabuleiro
					&& pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, coluna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, coluna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, coluna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else{
					break;
				}
			}
			verificaLinha = linha;

			// verifica a esquerda
			while ((--verificaLinha) > -1 && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, coluna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, coluna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, coluna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else
					break;
			}

			return pontuacaoCasa;
		}
		
		/**
		 * A partir da posição da peça é percorrido a diagonal direita ("\") para verificar qntidade de peças consecutivas e casas vazias
		 * 
		 * @param linha
		 * @param coluna
		 * @param jogador
		 */
		protected PontuacaoCasa pontuarDiagonalDireita(Integer linha, Integer coluna, String jogador) {

			PontuacaoCasa pontuacaoCasa = new PontuacaoCasa(0, 1);
			pontuacaoCasa.posicaoPecas.put(linha, coluna, jogador);
			int verificaLinha = linha;
			int verificaColuna = coluna;
			// verifica a direita
			while ((++verificaLinha) < GomokuJogo.tamanhoTabuleiro
					& (++verificaColuna) < GomokuJogo.tamanhoTabuleiro
					&& pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else{
					break;
				}
			}
			verificaColuna = coluna;
			verificaLinha = linha;

			// verifica a esquerda
			while ((--verificaLinha) > -1 & (--verificaColuna) > -1 && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else
					break;
			}
			return pontuacaoCasa;
		}
		
		
		/**
		 * A partir da posição da peça é percorrido a diagonla esquerda ("/") para verificar qntidade de peças consecutivas e casas vazias
		 * 
		 * @param linha
		 * @param coluna
		 * @param jogador
		 */
		protected PontuacaoCasa pontuarDiagonalEsquerda(Integer linha, Integer coluna, String jogador) {

			PontuacaoCasa pontuacaoCasa = new PontuacaoCasa(0, 1);
			pontuacaoCasa.posicaoPecas.put(linha, coluna, jogador);
			int verificaLinha = linha;
			int verificaColuna = coluna;
			// verifica a direita
			while ((++verificaLinha) < GomokuJogo.tamanhoTabuleiro
					& (--verificaColuna) > -1
					&& pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else{
					break;
				}
			}
			verificaColuna = coluna;
			verificaLinha = linha;

			// verifica a esquerda
			while ((--verificaLinha) > -1 & (++verificaColuna) < GomokuJogo.tamanhoTabuleiro && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
				if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(jogador)) {
					pontuacaoCasa.qntPecasConsecutiva++;
					pontuacaoCasa.posicaoPecas.put(verificaLinha, verificaColuna, jogador);
				} else if (this.tabuleiro.getValorCasa(verificaLinha, verificaColuna).equals(GomokuJogo.VAZIO)) {
					pontuacaoCasa.qntCasasLivres++;
					if (pontuacaoCasa.qntCasasLivres > GomokuJogo.check) {
						break;
					} 
					continue;
				} else
					break;
			}
			return pontuacaoCasa;
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
		
		@Override
		public String toString() {
			String string = "";
			string += "CasasLivres = " + qntCasasLivres;
			string += "\nPecasConsecutivas = " + qntPecasConsecutiva;
			string += "\nPos = " + posicaoPecas.toString();
			return string;
		}
	}
	
}
