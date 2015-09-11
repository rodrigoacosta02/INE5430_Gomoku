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

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void potuacaoPecasConsecutivasTest() throws Exception{
		RegrasPontuacao rp = new RegrasPontuacao();
		PontuacaoCasa pc = new PontuacaoCasa(3, 3);
		Table<Integer, Integer, String> tabela = HashBasedTable.create();
		tabela.put(1, 0, GomokuJogo.BRANCA);
		tabela.put(1, 1, GomokuJogo.PRETA);
		tabela.put(0, 1, GomokuJogo.PRETA);
		pc.posicaoPecas.putAll(tabela);
		
		rp.potuacaoPecasConsecutivas(pc, GomokuJogo.BRANCA);
		
		System.out.println(rp.getPontuacao());
	}
	
//	@Test
//	public final void testPontuacaoLinha() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testPotuarLinha() {
//		RegrasPontuacao rp = new RegrasPontuacao();
////		rp.atualizaPontuacao(4, GomokuJogo.PRETA, true);
//		System.out.println();
//		PontuacaoCasa pc = new PontuacaoCasa(2, 3);
//		String jog = GomokuJogo.BRANCA;
//		Table<Integer, Integer, String> tabela = HashBasedTable.create();
//		tabela.put(1, 0, GomokuJogo.BRANCA);
//		tabela.put(1, 1, GomokuJogo.PRETA);
////		tabela.put(0, 1, GomokuJogo.PRETA);
//		
//		
//		Tabuleiro tab = new Tabuleiro(tabela);
//		
//		rp.pontuacaoLinha(tab);
//
//		System.out.println(rp.getPontuacao());
//	}

	
//	@Test
//	public final void testAtualizaPontuacao() throws Exception {
//		RegrasPontuacao rp = new RegrasPontuacao();
////		rp.atualizaPontuacao(4, GomokuJogo.PRETA, true);
//		System.out.println();
//		PontuacaoCasa pc = new PontuacaoCasa(2, 3);
//		String jog = GomokuJogo.BRANCA;
//		for (int i = 1; i < 5; i++) {
//			pc = new PontuacaoCasa(4, i);
//			jog = jog.equals(GomokuJogo.BRANCA)? GomokuJogo.PRETA : GomokuJogo.BRANCA;
//			rp.atualizaPontuacao(pc, jog);
//			System.out.println(rp.getPontuacao());
//			System.out.println();
//		}
//	}

}
