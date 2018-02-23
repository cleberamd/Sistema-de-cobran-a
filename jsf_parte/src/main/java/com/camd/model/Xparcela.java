package com.camd.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Transient;



@Entity
@Table(name = "xparcela")

public class Xparcela {
	@Id
	private int idlan;
	private String ccusto;
	private int idboleto;
	private String nossonumero;
	private double valor;
	private Date vencimento;
	private String classFinanceira;
	private boolean renegociada; 
	private String idProcesso;
	@ManyToOne
	@JoinColumn(name = "cliente_codcfo")
	private Cliente cliente;
	@Transient
	private List<Taxas> taxas;
	@Transient
	private int diasAtraso;
	@Transient
	private double totalJuros;
	@Transient
	private String codcfo;
	
	
	






	public String getIdProcesso() {
		return idProcesso;
	}




	public void setIdProcesso(String idProcesso) {
		this.idProcesso = idProcesso;
	}




	public boolean isRenegociada() {
		return renegociada;
	}




	public void setRenegociada(boolean renegociada) {
		this.renegociada = renegociada;
	}




	public Xparcela() {
		super();
		this.taxas = new ArrayList<>();
		
	}
	



	public void setTaxas(List<Taxas> taxas) {
		this.taxas = taxas;
	}




	public String getClassFinanceira() {
		return classFinanceira;
	}




	public void setClassFinanceira(String classFinanceira) {
		this.classFinanceira = classFinanceira;
	}




	public double getTotalJuros() {
		return totalJuros;
	}

	public void setTotalJuros(double totalJuros) {
		this.totalJuros = totalJuros;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public List<Taxas> getTaxas() {
		return taxas;
	}

	public void setTaxas(Taxas taxas) {
		this.taxas.add(taxas);
	}

	public int getDiasAtraso() {
		return diasAtraso;
	}

	public void setDiasAtraso(int diasAtraso) {
		this.diasAtraso = diasAtraso;
	}

	

	public String getCodcfo() {
		return codcfo;
	}

	public void setCodcfo(String codcfo) {
		this.codcfo = codcfo;
	}

	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCcusto() {
		return ccusto;
	}

	public void setCcusto(String ccusto) {
		this.ccusto = ccusto;
	}

	public int getIdlan() {
		return idlan;
	}

	public void setIdlan(int islan) {
		this.idlan = islan;
	}

	public int getIdboleto() {
		return idboleto;
	}

	public void setIdboleto(int idboleto) {
		this.idboleto = idboleto;
	}

	public String getNossonumero() {
		return nossonumero;
	}

	public void setNossonumero(String nossonumero) {
		this.nossonumero = nossonumero;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}







}
