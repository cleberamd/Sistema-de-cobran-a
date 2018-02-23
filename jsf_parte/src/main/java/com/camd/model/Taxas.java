package com.camd.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "taxas")
public class Taxas {
	@Id
	private int cod;
	private String nome;
	private double percentual;
	@Transient
	private double valor;

	
	
	
	



	public Taxas() {
		super();
		
	}







	public Taxas(String nome2) {
		this.nome = nome2;
	}






	public double getPercentual() {
		return percentual;
	}






	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}




	public int getCod() {
		return cod;
	}






	public void setCod(int cod) {
		this.cod = cod;
	}






	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public double getValor() {
		return valor;
	}



	public void setValor(double valor) {
		this.valor = valor;
	}




	
}
