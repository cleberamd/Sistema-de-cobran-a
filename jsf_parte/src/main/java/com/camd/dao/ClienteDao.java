package com.camd.dao;



import java.util.List;

import com.camd.model.Cliente;

public interface ClienteDao  {
	public List<Cliente> AtualizarParcClie() ;
	public void save(Cliente cliente);
	public List<Cliente> getClientes();
	public void limpaTabela();


}