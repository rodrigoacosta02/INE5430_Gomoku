package com.aguiarcampos.gomoku.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import lombok.Getter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class RegrasPontuacao {

	@Getter
	private int pontuacao = 0;
	private Set<PontuacaoCasa> pontuadasEmLinha = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> pontuadasEmColuna = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> pontuadasEmDiagonalDireita = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	private Set<PontuacaoCasa> pontuadasEmDiagonalEsquerda = new HashSet<RegrasPontuacao.PontuacaoCasa>();

	private Set<Cell<Integer, Integer, String>> casasPontuadasEmLinha = new HashSet<Cell<Integer,Integer,String>>();
	private Set<Cell<Integer, Integer, String>> casasPontuadasEmColuna = new HashSet<Cell<Integer, Integer, String>>();
	private Set<Cell<Integer, Integer, String>> casasPontuadasEmDiagonalDireita = new HashSet<Cell<Integer, Integer, String>>();
	private Set<Cell<Integer, Integer, String>> casasPontuadasEmDiagonalEsquerda = new HashSet<Cell<Integer, Integer, String>>();

	private Tabuleiro tabuleiro;
	
	public RegrasPontuacao(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public boolean verificaVencedor(){
		GomokuJogo gj = new GomokuJogo();
		gj.tabela.putAll(tabuleiro.tabela);
		for (Cell<Integer, Integer, String> casa : tabuleiro.tabela.cellSet()) {
			if (gj.verificarJogada(casa.getRowKey(), casa.getColumnKey(), casa.getValue())) {
				if (tabuleiro.jogadorAtual.equals(GomokuJogo.PRETA)) {
					pontuacao = Integer.MAX_VALUE;
				} else {
					pontuacao = Integer.MIN_VALUE;
				}
				
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
			if (!casasPontuadasEmLinha.contains(casa)) {
				PontuacaoCasa pc = pontuarLinha(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
				if (pc.qntPecasConsecutiva > 1 && pc.qntCasasLivres > 0) {
					pontuadasEmLinha.add(pc);
					casasPontuadasEmLinha.addAll(pc.posicaoPecas.cellSet());
				}
			}
			
			if (!casasPontuadasEmColuna.contains(casa)) {
				PontuacaoCasa pc = pontuarColuna(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
				if (pc.qntPecasConsecutiva > 1 && pc.qntCasasLivres > 0) {
					pontuadasEmColuna.add(pc);
					casasPontuadasEmColuna.addAll(pc.posicaoPecas.cellSet());
				}
			}
			
			if (!casasPontuadasEmDiagonalDireita.contains(casa)) {
				PontuacaoCasa pc = pontuarDiagonalDireita(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
				if (pc.qntPecasConsecutiva > 1 && pc.qntCasasLivres > 0) {
					pontuadasEmDiagonalDireita.add(pc);
					casasPontuadasEmDiagonalDireita.addAll(pc.posicaoPecas.cellSet());
				}
			}
			
			if (!casasPontuadasEmDiagonalEsquerda.contains(casa)) {
				PontuacaoCasa pc = pontuarDiagonalEsquerda(casa.getRowKey(), casa.getColumnKey(), casa.getValue());
				if (pc.qntPecasConsecutiva > 1 && pc.qntCasasLivres > 0) {
					pontuadasEmDiagonalEsquerda.add(pc);
					casasPontuadasEmDiagonalEsquerda.addAll(pc.posicaoPecas.cellSet());
				}
			}
		}
		pontuar();
//		System.out.println(pontuacao + " - " + tabuleiro.toString());
	}

	private void pontuar() {
		for (PontuacaoCasa pontuacaoCasa : pontuadasEmLinha) {
			try {
				pontuacaoPecasConsecutivas(pontuacaoCasa);
			} catch (Exception e) {
				System.err.println(e.getMessage() + " - linha\n");
			}
		}
		 for (PontuacaoCasa pontuacaoCasa : pontuadasEmColuna) {
			 try {
					pontuacaoPecasConsecutivas(pontuacaoCasa);
				} catch (Exception e) {
					System.err.println(e.getMessage()+ " - coluna\n");
				}
		}
		 for (PontuacaoCasa pontuacaoCasa : pontuadasEmDiagonalDireita) {
			 try {
					pontuacaoPecasConsecutivas(pontuacaoCasa);
				} catch (Exception e) {
					System.err.println(e.getMessage()+ " - diag \\\n");
				}
		}
		 for (PontuacaoCasa pontuacaoCasa : pontuadasEmDiagonalEsquerda) {
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
			case 2:
				if (vezIA) {
					pontuacao += 100;
				} else{
					pontuacao -= 300;					
				}
				
				break;
			case 3:
				if (vezIA) {
					pontuacao += 250;
				} else{
					pontuacao -= 450;					
				}	
				
				break;
			case 4:
				if (vezIA) {
					pontuacao += 2400;
				} else{
					pontuacao -= 4200;					
				}	
				
				break;
			case 5:
				if (vezIA) {
					pontuacao = 7483648;
				} else{
					pontuacao = -7483648;					
				}	
				break;

			default:
				break;
		}
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
