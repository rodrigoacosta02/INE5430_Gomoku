package com.aguiarcampos.gomoku.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

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
	public final void testaAtualizarPossiveisJogadas() {
		Tabuleiro tab = new Tabuleiro();
		
		int linha[] = tab.linha();
		int coluna[] = tab.coluna();
		assertEquals(0, linha[0]);
		assertEquals(GomokuJogo.tamanhoTabuleiro, linha[1]);

		assertEquals(0, coluna[0]);
		assertEquals(GomokuJogo.tamanhoTabuleiro, coluna[1]);
		
		Table<Integer, Integer, String> aux = HashBasedTable.create();
		aux.put(2, 3, GomokuJogo.PRETA);
		
		tab.tabela.putAll(aux);
		
		linha = tab.linha();
		coluna = tab.coluna();
		assertEquals(0, linha[0]);
		assertEquals(2+GomokuJogo.check, linha[1]);

		assertEquals(0, coluna[0]);
		assertEquals(3+GomokuJogo.check, coluna[1]);
		
		
		
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
