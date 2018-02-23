package com.camd.dao;


import java.util.List;

import com.camd.model.Anotacao;
import com.camd.model.Cliente;
import com.camd.model.ParcelaR;
import com.camd.model.Taxas;
import com.camd.model.Xparcela;

public interface XparcelaDao {


	public List<Xparcela> listaparcela() ;
	public void save(Anotacao anotacao);
	public void save(Xparcela xparcela);
	public void save(ParcelaR parcelaR );
	public void save(Taxas taxa);
	public List<Taxas> listaTaxas();
	public void delete(Xparcela xparcela);
	public List<Anotacao> listaNota(Cliente c);
	public List<Cliente> listaClienteR();
	public void cancelaRenegociacao(List<ParcelaR> parcelasAEfetivar);
	public void delete(ParcelaR parcelaR);
	public void delete(Anotacao anotacao);
}
