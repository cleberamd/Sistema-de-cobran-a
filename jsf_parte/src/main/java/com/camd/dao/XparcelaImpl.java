package com.camd.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.camd.model.Anotacao;
import com.camd.model.Cliente;
import com.camd.model.ParcelaR;
import com.camd.model.Taxas;
import com.camd.model.Xparcela;

public class XparcelaImpl implements XparcelaDao {

	private Session session;
	

	public void setSession(Session session) {
		this.session = session;
	}

	
	@Override
	public void save(Xparcela xparcela) {
		session.saveOrUpdate(xparcela);

	}

	public boolean resultDuplic() {

		return false;

	}

	@Override
	public List<Xparcela> listaparcela() {
		Criteria criteria = session.createCriteria(Xparcela.class);
		List<Xparcela> listaParcel = criteria.list();

		return listaParcel;

	}
	


	@Override
	public void save(ParcelaR parcelaR) {

		session.saveOrUpdate(parcelaR);
	}

	@Override
	public void save(Taxas taxa) {
		session.saveOrUpdate(taxa);

	}

	@Override
	public List<Taxas> listaTaxas() {
		Criteria criteria = session.createCriteria(Taxas.class);
		List<Taxas> listaTaxas = criteria.list();

		return listaTaxas;
	}


	@Override
	public void delete(Xparcela xparcela) {
		Criteria criteria = session.createCriteria(Xparcela.class);
		session.delete(xparcela);
	}
	
	@Override
	public void delete(ParcelaR parcelaR) {
		Criteria criteria = session.createCriteria(ParcelaR.class);
		session.delete(parcelaR);
	}


	@Override
	public void save(Anotacao anotacao) {
		session.saveOrUpdate(anotacao);
		
	}
	
	@Override
	public void delete(Anotacao anotacao) {
		Criteria criteria = session.createCriteria(Anotacao.class);
		session.delete(anotacao);
	}


	@Override
	public List<Anotacao> listaNota(Cliente c) {
		Criteria criteria = session.createCriteria(Anotacao.class);
		criteria.add(Restrictions.eq("cliente",c));
		criteria.addOrder(Order.desc("id"));
		List<Anotacao> listaNota = criteria.list();

		return listaNota;
	}


	@Override
	public List<Cliente> listaClienteR() {
		Criteria criteria = session.createCriteria(Cliente.class);
		criteria.add(Restrictions.isNotEmpty("parcelasR"));
		List<Cliente> listaClienteR = criteria.list();
		
		
		return listaClienteR;
		
	}


	@Override
	public void cancelaRenegociacao(List<ParcelaR> parcelasAEfetivar) {
		
		List<Xparcela> xP;
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < parcelasAEfetivar.size(); i++){
			Criteria criteria = session.createCriteria(Xparcela.class);
			criteria.add(Restrictions.eq("idProcesso",parcelasAEfetivar.get(i).getIdProcesso().toString()));
			xP = criteria.list();
			ids.add(parcelasAEfetivar.get(i).getIdProcesso());
			for (Xparcela xparcela : xP) {
				xparcela.setRenegociada(false);
				xparcela.setIdProcesso(null);
				save(xparcela);
			}
			
						
		}
		Query q = session.createQuery("DELETE FROM ParcelaR pr WHERE pr.idProcesso IN (:list)");

		q.setParameterList("list", ids);
		q.executeUpdate();
		
		
		
	}

}
