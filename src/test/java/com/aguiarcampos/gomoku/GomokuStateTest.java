package com.aguiarcampos.gomoku;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import com.aguiarcampos.gomoku.core.GomokuJogo;

public class GomokuStateTest {
	static GomokuJogo gs;
	static Scanner inFile;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
	}

	@Test
	public final void testGomokuState() {
		int limiteLinha = 15;
		int limiteColuna = 15;
		gs = new GomokuJogo();
		
		assertEquals(limiteLinha, gs.getPosicaoPecas().rowKeySet().size());
		assertEquals(limiteColuna, gs.getPosicaoPecas().columnKeySet().size());
		assertTrue(gs.PRETA.equals(gs.getJogadorAtual()));
		for (int i = 0; i < limiteLinha; i++) {
			for (int j = 0; j < limiteColuna; j++) {
				assertTrue(gs.VAZIO.equals(gs.getPosicaoPecas().get(i, j)));
			}
		}
	}

	@Test
	public final void testGetValorCasa() {
		System.out.println(gs.getPosicaoPecas().containsValue(gs.BRANCA));
		gs = new GomokuJogo();
		for (int i = 0; i < 4; i++) {
			int linha = (int) (Math.random() * 15);
			int coluna = (int) (Math.random() * 15);
			gs.realizarJogada(linha, coluna);
			assertTrue(gs.getValorCasa(linha, coluna).equals(gs.PRETA));

			linha = (int) (Math.random() * 15);
			coluna = (int) (Math.random() * 15);
			gs.realizarJogada(linha, coluna);
			assertTrue(gs.getValorCasa(linha, coluna).equals(gs.BRANCA));
		}
	}

	@Test
	public final void testGetJogadorAtual() throws FileNotFoundException {
		gs = new GomokuJogo();
		inFile = new Scanner(new FileReader("/home/user_ufsc/teste"));
		int jogada = 0;
		int linha;
		int coluna;
		while (inFile.hasNext()) {
			String jogador = jogada % 2 == 0 ? gs.PRETA : gs.BRANCA;
			linha = inFile.nextInt();
			coluna = inFile.nextInt();
			assertTrue(gs.getJogadorAtual().equals(jogador));
			assertTrue(gs.realizarJogada(linha, coluna));
			jogada++;

		}
		inFile.close();
	}

	@Test
	public final void testGetVencedor() throws FileNotFoundException {
		gs = new GomokuJogo();
		inFile = new Scanner(new FileReader("/home/user_ufsc/teste"));
		int linha;
		int coluna;
		int jogadas = 0;
		while (inFile.hasNext()) {

			linha = inFile.nextInt();
			coluna = inFile.nextInt();

			assertTrue(gs.getVencedor().equals(gs.VAZIO));
			assertTrue(gs.realizarJogada(linha, coluna));
			jogadas++;
		}
		
		assertTrue(gs.getVencedor().equals(gs.BRANCA));
	}

	@Test
	public final void testRealizarJogada() throws FileNotFoundException {
		gs = new GomokuJogo();
		inFile = new Scanner(new FileReader("/home/user_ufsc/teste"));
		int linha;
		int coluna;
		int jogadas = 0;
		while (inFile.hasNext()) {

			linha = inFile.nextInt();
			coluna = inFile.nextInt();

			assertTrue(gs.realizarJogada(linha, coluna));
			jogadas++;
		}
		// TODO
	}

}
