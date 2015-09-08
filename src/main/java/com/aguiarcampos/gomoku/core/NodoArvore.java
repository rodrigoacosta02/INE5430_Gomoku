package com.aguiarcampos.gomoku.core;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class NodoArvore<T> {

	@Getter
	@Setter
	private T nodoArvore;

	@Getter
	@Setter
	private NodoArvore<T> pai;

	@Getter
	@Setter
	private Set<NodoArvore<T>> filhos;
	
	
	public NodoArvore() {
		filhos = new HashSet<NodoArvore<T>>();
	}

	public NodoArvore(T nodoArvore) {
		this();
		this.nodoArvore = nodoArvore;
	}
	
	public void addFilho(NodoArvore<T> filho) {
		filho.setPai(this);
		filhos.add(filho);
	}
	
	public void addListaFilhos(Set<NodoArvore<T>> filhos) {
		for (NodoArvore<T> filho : filhos) {
			filho.setPai(this);
		}
		filhos.addAll(filhos);
	}
	
	public void removerFilho(NodoArvore<T> filho){
		this.filhos.remove(filho);
	}
	
	public void removerTodosFilhos() {
		this.filhos.clear();
	}
	
	public int numFilhos(){
		return filhos.size();
	}

}
