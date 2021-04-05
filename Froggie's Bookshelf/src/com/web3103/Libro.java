package com.web3103;

public class Libro {
	
	private String titulo, autor, isbn;
	private boolean estado;
	private int libroID;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public int getLibroID() {
		return libroID;
	}
	public void setLibroID(int libroID) {
		this.libroID = libroID;
	}
	
	public Libro() {
	}
	
	

}
