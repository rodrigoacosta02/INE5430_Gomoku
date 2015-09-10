package com.aguiarcampos.gomoku.core;

import org.junit.BeforeClass;
import org.junit.Test;

import com.aguiarcampos.gomoku.core.GomokuJogo;
import com.aguiarcampos.gomoku.core.IA_teste;
import com.aguiarcampos.gomoku.core.Tabuleiro;
import com.aguiarcampos.gomoku.core.IA_teste.MelhorJogada;

public class IA_testeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public final void testMiniM() throws Exception {

		int prof = 3;
		Tabuleiro tab = new Tabuleiro();
		tab.moverPeca(0, 0, GomokuJogo.PRETA);
		
		tab.moverPeca(1, 0, GomokuJogo.BRANCA);
		
		tab.moverPeca(0, 2, GomokuJogo.PRETA);
		
		IA_teste ia = new IA_teste();
		
		MelhorJogada mj = ia.miniM(prof, tab, GomokuJogo.PRETA);
		
		System.out.println(mj.pontuacao + " pontuacao");
		System.out.println(mj.jogada.getLocation());
		
		
	}

}
