package com.camd.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "nota")
@SequenceGenerator(name = "seq", sequenceName = "seq_nota", allocationSize = 1, initialValue = 1)
public class Anotacao {
	
	@Id
	@GeneratedValue(generator="seq")
	private int id;
	private Date data;
	private String texto;
	private String usuario;
	@ManyToOne
	@JoinColumn(name = "cliente_codcfo")
	private Cliente cliente;
	
	

	public int getId() {
		return id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
