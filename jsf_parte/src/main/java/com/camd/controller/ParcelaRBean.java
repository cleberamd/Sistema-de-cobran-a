package com.camd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import com.camd.business.XparcelaBusiness;
import com.camd.model.Cliente;
import com.camd.model.ParcelaR;

@ManagedBean(name = "parcelaR")
@ViewScoped 
public class ParcelaRBean {
	private List<Cliente> listaclienteR;
	private List<ParcelaR> parcelasSelecionadas;
	private List<ParcelaR> parcelasAEfetivar = new ArrayList<>();
	private List<Cliente> clienteFiltrado ;
	private String[] statusP ;
	private FacesMessage msg;
	@PostConstruct
	public void init() {
		listaClinteR();
		statusP = new String[2];
		statusP[0] = "Pendente";
		statusP[1] = "Efetivado"; 
	
	}

	public void listaClinteR() {
	
			XparcelaBusiness xpb = new XparcelaBusiness();
			listaclienteR = xpb.atualizaClienteR();
	
	}
	
	public void cancelaRenegociacao() {
		XparcelaBusiness xpb = new XparcelaBusiness();
		xpb.cancelaRenegociacao(parcelasAEfetivar);
		//parcelasAEfetivar.get(0).getCliente().getParcelasR().removeAll(parcelasAEfetivar);
		//listaclienteR.remove(parcelasAEfetivar.get(0).getCliente());
		listaclienteR.clear();
		listaClinteR();
		msg = new FacesMessage("Renegociação excluidas!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	
	
	
	public void efetivaParcelaG() {
		for (ParcelaR pR : parcelasAEfetivar) {
			pR.setStatus("Efetivado");
		}
		XparcelaBusiness xpb = new XparcelaBusiness();
		xpb.saveParcelaR(parcelasAEfetivar);
		listaclienteR.clear();
		listaClinteR();
		msg = new FacesMessage("Parcelas efetivadas!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	public void onRowSelect(SelectEvent event){
		System.out.println("onSelect ");
		parcelasAEfetivar.add(((ParcelaR) event.getObject()));
		
		}

		public void unRowSelect(UnselectEvent event){
			System.out.println("unSelect ");
			parcelasAEfetivar.remove(((ParcelaR) event.getObject()));
			
		}
	

	public void onRowSelectAll(ToggleSelectEvent event){
			
		DataTable listTemp = (DataTable) event.getSource();
		parcelasAEfetivar = (List<ParcelaR>) listTemp.getValue();
		  
		}
	
	
	//---------------Get & Set-------------------------------





	public List<Cliente> getListaclienteR() {
		return listaclienteR;
	}




	public String[] getStatusP() {
		return statusP;
	}

	public void setStatusP(String[] statusP) {
		this.statusP = statusP;
	}

	public List<Cliente> getClienteFiltrado() {
		return clienteFiltrado;
	}

	public void setClienteFiltrado(List<Cliente> clienteFiltrado) {
		this.clienteFiltrado = clienteFiltrado;
	}

	public List<ParcelaR> getParcelasAEfetivar() {
		return parcelasAEfetivar;
	}

	public void setParcelasAEfetivar(List<ParcelaR> parcelasAEfetivar) {
		this.parcelasAEfetivar = parcelasAEfetivar;
	}

	public List<ParcelaR> getParcelasSelecionadas() {
		return parcelasSelecionadas;
	}



	public void setParcelasSelecionadas(List<ParcelaR> parcelasSelecionadas) {
		this.parcelasSelecionadas = parcelasSelecionadas;
	}



	public void setListaclienteR(List<Cliente> listaclienteR) {
		this.listaclienteR = listaclienteR;
	}

	

	
	
	
	
	
}
