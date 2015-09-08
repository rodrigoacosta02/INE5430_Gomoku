package com.aguiarcampos.gomoku.core;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Arvore generica  
 * @param <T>
 */
public class Arvore<T> {

	@Getter
	@Setter
	private NodoArvore<T> raiz;

//	public Arvore() {
//		super();
//	}

	public Arvore(NodoArvore<T> raiz) {
		this.raiz = raiz;
	}

//	public Arvore(T raiz) {
//		this();
//		this.raiz = new NodoArvore<T>(raiz);
//	}
	
	public boolean estaVazio() {
		return (this.raiz == null);
	}
	
	private void buildPostOrder(NodoArvore<T> nodo, Set<NodoArvore<T>> postOrder) {
		for (NodoArvore<T> filho : nodo.getFilhos()) {
			buildPostOrder(filho, postOrder);
		}
		postOrder.add(nodo);
	}



}
