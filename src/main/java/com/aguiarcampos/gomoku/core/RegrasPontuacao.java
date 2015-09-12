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
	Set<PontuacaoCasa> casasPontuadasEmLinha = new HashSet<RegrasPontuacao.PontuacaoCasa>();
	Set<PontuacaoCasa> casasPontuadasEmColuna;
	Set<PontuacaoCasa> casasPontuadasEmDiagonalDireita;
	Set<PontuacaoCasa> casasPontuadasEmDiagonalEsquerda;

	Tabuleiro tabuleiro;
	
	public RegrasPontuacao(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public void pontuacaoLinha(Tabuleiro tabuleiro) {

		// percorre casas preenchidas
		for (Cell<Integer, Integer, String> casa : tabuleiro.tabela.cellSet()) {
			casasPontuadasEmLinha.add(pontuarLinha(casa.getRowKey(), casa.getColumnKey(), casa.getValue()));
		}
		System.out.println(pontuacao);
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
			if (this.tabuleiro.getValorCasa(linha, coluna).equals(jogador)) {
				pontuacaoCasa.qntPecasConsecutiva++;
				pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
			} else if (this.tabuleiro.getValorCasa(linha, coluna).equals(GomokuJogo.VAZIO)) {
				pontuacaoCasa.qntCasasLivres++;
				continue;
			} else{
				break;
			}
		}
		verificaColuna = coluna;

		// verifica a esquerda
		while ((--verificaColuna) > -1 && pontuacaoCasa.qntPecasConsecutiva < GomokuJogo.check) {
			if (this.tabuleiro.getValorCasa(linha, coluna).equals(jogador)) {
				pontuacaoCasa.qntPecasConsecutiva++;
				pontuacaoCasa.posicaoPecas.put(linha, verificaColuna, jogador);
			} else if (this.tabuleiro.getValorCasa(linha, coluna).equals(GomokuJogo.VAZIO)) {
				pontuacaoCasa.qntCasasLivres++;
				continue;
			} else
				break;
		}

		try {
			pontuacaoPecasConsecutivas(pontuacaoCasa);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new PontuacaoCasa(0, 0);//não é a melhor maneira de tratar isso!!TODO
		}
		return pontuacaoCasa;
	}

	public void pontuacaoPecasConsecutivas(PontuacaoCasa pontuacaoCasa) throws Exception{
		
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
					pontuacao += 10;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 100;					
				}
				
				break;
			case 3:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 100;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 1000;					
				}	
				
				break;
			case 4:
				if (vezIA) {
					pontuacao += pontuacaoCasasLivresIA(pontuacaoCasa.qntCasasLivres);
					pontuacao += 1000;
				} else{
					pontuacao += pontuacaoCasasLivresAdversario(pontuacaoCasa.qntCasasLivres);
					pontuacao -= 10000;					
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
