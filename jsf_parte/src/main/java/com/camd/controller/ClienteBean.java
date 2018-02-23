package com.camd.controller;


import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.camd.business.ClienteBusiness;
import com.camd.model.Cliente;
import com.camd.model.Xparcela;

@ManagedBean(name = "cliente")
@RequestScoped
public class ClienteBean {
	private Cliente client ;
	private Xparcela xparcela ;
	//private List<Xparcela>parcelasSelecionadas;
	private List<Cliente> listaClient;
	//private List<Xparcela> parcelasClient = new ArrayList<>();
	private List<Cliente> clienteFiltrado ;
	FacesContext fc = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	



	@PostConstruct
	public void init() {
		
		System.out.println("estou no init");
		listaClient();
		
	}

	//--------vai para a tela de negociações pendentes-------------------------
	public String vaiparaRepPendente() {
		System.out.println("vai para");

		return "/user_common/reneg_pendentes";
	}
	
	public String vaiparaConfig() {
		System.out.println("vai para");

		return "/admin/config";
	}
	
	
	public String vaiparaParcCliente() {
		System.out.println("vai para");
		

		return "/user_common/listaPCliente";
	}
	
	
	public void listaClient() {
		
		ClienteBusiness clienteB = new ClienteBusiness();
		listaClient = clienteB.getListClientes();
		
		
	}
	
	
	
	 public void onRowSelect(SelectEvent event) {
		 
	        FacesMessage msg = new FacesMessage("Car Selected", ((Cliente) event.getObject()).getNome());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        session.setAttribute("parcelasC", ((Cliente) event.getObject()).getParcelas());
	        
	    }
	/* 
	    public void onRowUnselect(UnselectEvent event) {
	        FacesMessage msg = new FacesMessage("Car Unselected", ((Cliente) event.getObject()).getNome());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	
	*/
/*
	// metodo para atualizar Lista de parcelas e clientes do sistema.
	public void atualizar() {

		ClienteBusiness clienteBusiness = new ClienteBusiness();
		clienteBusiness.AtualizarParcClie();
	listaClient.clear();
		listaClient = clienteBusiness.getListClientes();

		
	}

	*/

	/*
	public void onRowSelect(SelectEvent event){
		System.out.println("onSelect ");
		Xparcela parc = ((Xparcela) event.getObject()); 
		parcelasClient.add(parc);
		
		session.setAttribute("parcelasC", this.parcelasClient);
		
		}

		public void unRowSelect(UnselectEvent event){
			System.out.println("unSelect ");
			
			Xparcela parc = ((Xparcela) event.getObject()); 
			parcelasClient.remove(parc);
			
			session.setAttribute("parcelasC", this.parcelasClient);
		}
	

	public void onRowSelectAll(ToggleSelectEvent event){

		   DataTable listTemp = (DataTable) event.getSource();
		   parcelasClient = (List<Xparcela>) listTemp.getValue();
		   
		   session.setAttribute("parcelasC", this.parcelasClient);
		   

		}
	*/
	
	

	
	/*
	public List<Xparcela> getParcelasClient() {
		
		return parcelasClient;
	}



	public void setParcelasClient(List<Xparcela> parcelasClient) {
		this.parcelasClient = parcelasClient;
	}


*/




	public List<Cliente> getClienteFiltrado() {
		return clienteFiltrado;
	}

	public void setClienteFiltrado(List<Cliente> clienteFiltrado) {
		this.clienteFiltrado = clienteFiltrado;
		
			System.out.println("cliente F--> ");

	}

	public List<Cliente> getListaClient() {
		return listaClient;
	}


/*
	public List<Xparcela> getParcelasSelecionadas() {
		return parcelasSelecionadas;
	}


	public void setParcelasSelecionadas(List<Xparcela> parcelasSelecionadas) {
		this.parcelasSelecionadas = parcelasSelecionadas;
		
	}
*/
	

	public void setListaClient(List<Cliente> listaClient) {
		this.listaClient = listaClient;
	}



	public Cliente getClient() {
		return client;
	}

	public void setClient(Cliente cliente) {
		this.client = cliente;
		
		session.setAttribute("parcelasC", this.client.getParcelas());
		System.out.println("setCliente" + cliente.getNome() );
		
				
	}

	public Xparcela getXparcela() {
		return xparcela;
	}

	public void setXparcela(Xparcela xparcela) {
		this.xparcela = xparcela;
		System.out.println("xparcela");
	}

}
