package com.camd.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;


@Entity
@Table(name = "cliente")
public class Cliente {
	@Id
	private String codcfo;
	private String nome;
	@NaturalId
	private String cpf;
	private String rua;
	private String numero;
	private String complemento;
	private String cidade;
	private String estado;
	private String cep;
	@Transient
	private double valorS;

	
	public double getValorS() {
		return valorS;
	}

	public void setValorS(double valorS) {
		this.valorS = valorS;
	}

	@OneToMany(mappedBy = "cliente", targetEntity = Xparcela.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Xparcela> parcelas;
	
	@OneToMany(mappedBy = "cliente", targetEntity = ParcelaR.class, fetch = FetchType.LAZY)
	private List<ParcelaR> parcelasR;
	
	@OneToMany(mappedBy = "cliente", targetEntity = Anotacao.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Anotacao> Anotacoes;
	
	
	
	
	public List<ParcelaR> getParcelasR() {
		return parcelasR;
	}

	public void setParcelasR(List<ParcelaR> parcelasR) {
		this.parcelasR = parcelasR;
	}

	public List<Anotacao> getAnotacoes() {
		return Anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		Anotacoes = anotacoes;
	}

	public List<Xparcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Xparcela> parcelas) {
		this.parcelas = parcelas;
	}

	public String getCodcfo() {
		return codcfo;
	}

	public void setCodcfo(String codcfo) {
		this.codcfo = codcfo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
