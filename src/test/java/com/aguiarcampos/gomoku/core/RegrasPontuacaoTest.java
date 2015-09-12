package com.aguiarcampos.gomoku.core;

import static org.junit.Assert.*;

import javax.swing.text.AbstractDocument.BranchElement;

import org.junit.Before;
import org.junit.Test;

import com.aguiarcampos.gomoku.core.GomokuJogo;
import com.aguiarcampos.gomoku.core.RegrasPontuacao;
import com.aguiarcampos.gomoku.core.Tabuleiro;
import com.aguiarcampos.gomoku.core.RegrasPontuacao.PontuacaoCasa;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class RegrasPontuacaoTest {
	RegrasPontuacao rp;
	PontuacaoCasa pc;
	Table<Integer, Integer, String> casasSequenciaisEmLinha;

	@Before
	public void setUp() throws Exception {
		rp = new RegrasPontuacao(null);
		pc = new PontuacaoCasa(3, 2);
		casasSequenciaisEmLinha = HashBasedTable.create();
		casasSequenciaisEmLinha.put(1, 0, GomokuJogo.PRETA);
		casasSequenciaisEmLinha.put(1, 1, GomokuJogo.PRETA);
		// tabela.put(0, 1, GomokuJogo.PRETA);
		pc.posicaoPecas.putAll(casasSequenciaisEmLinha);
	}

	@Test
	public final void testPontuacaoCasasAdversario() throws Exception {
		for (int casasLivres = 0; casasLivres < GomokuJogo.check; casasLivres++) {
			assertEquals(casasLivres * (-5),
					rp.pontuacaoCasasLivresAdversario(casasLivres));
		}
	}

	@Test
	public final void testPontuacaoCasasLivresIA() throws Exception {
		for (int casasLivres = 0; casasLivres < GomokuJogo.check; casasLivres++) {
			assertEquals(casasLivres * 5,
					rp.pontuacaoCasasLivresIA(casasLivres));
		}
	}

	@Test(expected = Exception.class)
	public final void testPontuacaoPecasConsecutivasException()
			throws Exception {
		pc = new PontuacaoCasa(GomokuJogo.check / 2, GomokuJogo.check / 2);
		rp.pontuacaoPecasConsecutivas(pc);
	}

	@Test
	public final void testPontuacaoPecasConsecutivas() throws Exception {
		// 3 casas livres = 15 pontos | 2 sequencias = 10 PRETAS | 100 BRANCAS
		pc = new PontuacaoCasa(3, 2);
		casasSequenciaisEmLinha.clear();
		casasSequenciaisEmLinha.put(1, 0, GomokuJogo.PRETA);
		casasSequenciaisEmLinha.put(1, 1, GomokuJogo.PRETA);
		pc.posicaoPecas.putAll(casasSequenciaisEmLinha);

		rp.pontuacaoPecasConsecutivas(pc);
		assertEquals(25, rp.getPontuacao());

		rp = new RegrasPontuacao(null);
		casasSequenciaisEmLinha.put(1, 0, GomokuJogo.BRANCA);
		casasSequenciaisEmLinha.put(1, 1, GomokuJogo.BRANCA);
		pc.posicaoPecas.clear();
		pc.posicaoPecas.putAll(casasSequenciaisEmLinha);

		rp.pontuacaoPecasConsecutivas(pc);
		assertEquals(-115, rp.getPontuacao());

	}

	@Test
	public final void testPontuarLinha() {
		fail("não implementado");
	}

}
