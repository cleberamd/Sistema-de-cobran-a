package com.camd.model;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "parcelaR")
@SequenceGenerator(name = "seq", sequenceName = "seq_par", allocationSize = 1, initialValue = 1)
public class ParcelaR {
	
	@Id
	@GeneratedValue(generator="seq")
	private int id;
	private String ccusto;
	private double valor;
	private Date vencimento;
	private String status;
	private String tipo;
	private String idProcesso;
	private Date criacao;
	private String usuarioCriacao;
	@Transient
	private int xVezes;

	
	@Transient
	private String dataAte;
	
	private double valorMaisTaxa;
	@ManyToOne
	@JoinColumn(name = "cliente_codcfo")
	private Cliente cliente;
	







	public String getIdProcesso() {
		return idProcesso;
	}




	public void setIdProcesso(String idProcesso) {
		this.idProcesso = idProcesso;
	}




	public String getDataAte() {
		return dataAte;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public void setDataAte(String dataAte) {
		this.dataAte = dataAte;
	}



	public int getxVezes() {
		return xVezes;
	}




	public void setxVezes(int xVezes) {
		this.xVezes = xVezes;
	}
	
	public String getTipo() {
		return tipo;
	}




	public double getValorMaisTaxa() {
		return valorMaisTaxa;
	}




	public void setValorMaisTaxa(double valorMaisTaxa) {
		this.valorMaisTaxa = valorMaisTaxa;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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


	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}




	public Date getCriacao() {
		return criacao;
	}




	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}




	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}




	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}






}
