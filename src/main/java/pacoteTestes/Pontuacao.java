package pacoteTestes;
import lombok.Getter;


public enum Pontuacao {
	
	CASA_ADVERSARIO(10),
	CASA_JOGADOR(10),
	
	SEQUENCIA_ADVERSARIO_2(300),
	SEQUENCIA_ADVERSARIO_3(1000),
	SEQUENCIA_ADVERSARIO_4(1500),
	SEQUENCIA_ADVERSARIO_5(3000),
	
	SEQUENCIA_JOGADOR_2(100),
	SEQUENCIA_JOGADOR_3(300),
	SEQUENCIA_JOGADOR_4(400),
	SEQUENCIA_JOGADOR_5(3000);
	
	@Getter
	private final int pontuacao;

	private Pontuacao (int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	
}
