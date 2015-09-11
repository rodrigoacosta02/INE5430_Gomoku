package pacoteTestes;
import com.aguiarcampos.gomoku.core.GomokuJogo;


public class RegrasPontuacao extends GomokuJogo {

	private int pontuacao;
	
	
	
	
	/**
	 * verificar possibilidades de vitória da jogada nas 4 direções possíveis
	 * 
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return True se existir vencedor
	 */
	public boolean verificarJogada(int linha, int coluna, String jogador) {
		if (verificarLinha(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarColuna(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarDiagonalDireita(linha, coluna, jogador) >= check) {
			return true;
		}
		if (verificarDiagonalEsquerda(linha, coluna, jogador) >= check) {
			return true;
		}
		return false;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em linha 
	 * @param linhaFixa
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarLinha(int linhaFixa, int coluna, String jogador) {
		int qntPecas = 1;
		int casasVazias = 0;
		while (tabuleiro.containsColumn(++coluna) && qntPecas < check) {
			if (tabuleiro.get(linhaFixa, coluna).equals(jogador)) {
				qntPecas++;
			}else if (tabuleiro.get(linhaFixa, coluna).equals(GomokuJogo.VAZIO) ) {
				casasVazias++;
			} else
				break;
		}
		//retorna ao valor coluna ao seu ponto original
		coluna -= qntPecas;
		while (tabuleiro.containsColumn(--coluna) && qntPecas < check) {
			if (tabuleiro.get(linhaFixa, coluna).equals(jogador)) {
				qntPecas++;
			}else if (tabuleiro.get(linhaFixa, coluna).equals(GomokuJogo.VAZIO) ) {
				casasVazias++;
			} else
				break;
		}
		return qntPecas;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em coluna
	 * @param linha
	 * @param colunaFixa
	 * @param jogador
	 * @return
	 */
	public int verificarColuna(int linha, int colunaFixa, String jogador) {
		int contador = 1;

		while (tabuleiro.containsRow(++linha) && contador < check) {
			if (tabuleiro.get(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor linha ao seu ponto original
		linha -= contador;
		while (tabuleiro.containsRow(--linha) && contador < check) {
			if (tabuleiro.get(linha, colunaFixa).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em diagonal ("\")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarDiagonalDireita(int linha, int coluna, String jogador) {
		int contador = 1;

		while (tabuleiro.containsRow(++linha)
				& tabuleiro.containsColumn(++coluna)  && contador < check) {
			if (tabuleiro.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna -= contador;
		while (tabuleiro.containsRow(--linha)
				&& tabuleiro.containsColumn(--coluna) && contador < check) {
			if (tabuleiro.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	/**
	 * verifica se existem o mínimo de 5 casas do mesmo jogador em diagonal ("/")
	 * @param linha
	 * @param coluna
	 * @param jogador
	 * @return
	 */
	public int verificarDiagonalEsquerda(int linha, int coluna, String jogador) {
		int contador = 1;

		while (tabuleiro.containsRow(++linha)
				& tabuleiro.containsColumn(--coluna) && contador < check) {
			if (tabuleiro.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		//retorna ao valor coluna e linha ao seu ponto original
		linha -= contador;
		coluna += contador;
		while (tabuleiro.containsRow(--linha)
				&& tabuleiro.containsColumn(++coluna) && contador < check) {
			if (tabuleiro.get(linha, coluna).equals(jogador))
				contador++;
			else
				break;
		}
		return contador;
	}

	
}
