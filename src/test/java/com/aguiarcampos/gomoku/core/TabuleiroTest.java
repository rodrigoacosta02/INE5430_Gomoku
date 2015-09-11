package com.aguiarcampos.gomoku.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TabuleiroTest {

	ExpectedException exception = ExpectedException.none();
	Tabuleiro tab;

	@Before
	public void setup() throws Exception {
		tab = new Tabuleiro();
	}

	@Test
	public final void testMoverPeca() throws Exception {
		tab = new Tabuleiro();

		int linha = 0;
		int coluna = 1;
		tab.moverPeca(linha, coluna, GomokuJogo.BRANCA);
		assertEquals(1, tab.tabela.size());

		assertEquals(tab.tabela.containsValue(GomokuJogo.BRANCA), true);
		assertEquals(tab.tabela.containsValue(GomokuJogo.VAZIO), false);
		assertEquals(tab.tabela.containsValue(GomokuJogo.PRETA), false);

		linha = 1;
		coluna = 0;
		tab.moverPeca(linha, coluna, GomokuJogo.PRETA);
		assertEquals(2,tab.tabela.size());

		assertEquals(tab.tabela.containsValue(GomokuJogo.BRANCA), true);
		assertEquals(tab.tabela.containsValue(GomokuJogo.VAZIO), false);
		assertEquals(tab.tabela.containsValue(GomokuJogo.PRETA), true);

	}
	@Test(expected = Exception.class)
	public final void testMoverPecaException() throws Exception {
		int linha = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
		int coluna = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
		tab.moverPeca(linha, coluna, GomokuJogo.VAZIO);
	}
	
	@Test(expected = Exception.class)
	public final void testMoverPecaException2() throws Exception {
		int linha = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
		int coluna = (int) (Math.random() * GomokuJogo.tamanhoTabuleiro);
		tab.moverPeca(linha, coluna, GomokuJogo.BRANCA);
		assertEquals(1, tab.tabela.size());
		
		tab.moverPeca(linha, coluna, GomokuJogo.PRETA);
		assertEquals(GomokuJogo.PRETA, tab.getValorCasa(linha, coluna));
	}
	
	@Test
	public final void testGetValorCasa() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCopia() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testLimpaCasa() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetPossiveisJogadas() {
		fail("Not yet implemented"); // TODO
	}

}
