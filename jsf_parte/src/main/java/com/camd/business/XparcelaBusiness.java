package com.camd.business;

import java.util.ArrayList;
import java.util.List;

import com.camd.dao.XparcelaDao;
import com.camd.dao.utils.DAOFactory;
import com.camd.model.Anotacao;
import com.camd.model.Cliente;
import com.camd.model.ParcelaR;
import com.camd.model.Taxas;
import com.camd.model.Xparcela;

public class XparcelaBusiness {

	private XparcelaDao xparcelaDao;
	
	public XparcelaBusiness() {
		super();
		setXparcelaDao(DAOFactory.createXparcela());
	}


	public void setXparcelaDao(XparcelaDao xparcelaDao) {
		this.xparcelaDao = xparcelaDao;
	}
	
	 public void save(Xparcela xparcela) {
		 xparcelaDao.save(xparcela);
		 
	 }
	 

	 public List<Xparcela> getListaparcela(Cliente c){
		
		 List<Xparcela> listaXparc = c.getParcelas();
		 return listaXparc;
		 
	 }
	 
	 public List<Taxas> getListaTaxas(){
			
		 List<Taxas> listaTaxas = xparcelaDao.listaTaxas();
		 return listaTaxas;
		 
	 }


	public void AtualizarValorTaxa(List<Taxas> t) {
		
		for (Taxas taxas : t) {
			 xparcelaDao.save(taxas);
		}
		
	}
	
	public void AtualizarValorTaxa(Taxas t) {
		
		
			 xparcelaDao.save(t);
		
		
	}


	public void saveParcelaR(List<ParcelaR> parcelasGeradas) {
		
		for (ParcelaR parcelaR : parcelasGeradas) {
			xparcelaDao.save(parcelaR);
		}
		
		
	}


	public void deletaparcela(List<Xparcela> listaParcela) {
		for (Xparcela xparcela : listaParcela) {
			xparcelaDao.delete(xparcela);
		}
		
		
		
	}


	public void salvaAnotacao(List<Anotacao> listNota) {
	
		for (Anotacao anotacao : listNota) {
			xparcelaDao.save(anotacao);
		}
		
	}
	 
	 public List<Anotacao> atualizaNota(Cliente c) {
		
		return xparcelaDao.listaNota(c);
		 
	 }


	public List<Cliente> atualizaClienteR() {
		List<Cliente>listaClienteR = new ArrayList<>();
		listaClienteR= xparcelaDao.listaClienteR();
		for (Cliente c1 : listaClienteR) {
			if (!c1.getParcelasR().isEmpty()) {

				for (ParcelaR parc : c1.getParcelasR()) {

					c1.setValorS(c1.getValorS() + parc.getValorMaisTaxa());
				}

				

			}
		}
		return listaClienteR;
	}


	public void cancelaRenegociacao(List<ParcelaR> parcelasAEfetivar) {
		xparcelaDao.cancelaRenegociacao(parcelasAEfetivar);
		
	}
	 
	

}
