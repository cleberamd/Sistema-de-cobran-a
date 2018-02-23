package com.camd.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.runtime.directive.Foreach;

import com.camd.dao.ClienteDao;
import com.camd.dao.utils.DAOFactory;
import com.camd.model.Cliente;
import com.camd.model.Xparcela;

public class ClienteBusiness {

	private ClienteDao clienteDao;

	public ClienteBusiness() {
		super();

		setClienteDao(DAOFactory.createCliente());
	}

	// esse metodo depois vai retornar a Lista de clientes e parcelas
	public void AtualizarParcClie() {

		//clienteDao.limpaTabela();
		List<Cliente> listaCliente = clienteDao.AtualizarParcClie();

		save(listaCliente);

	}

	// -----------Salva/Atualiza lista de clientes
	// -------------------------------------
	public void save(List<Cliente> cliente) {
		List<Cliente> listaC = clienteDao.getClientes();
		boolean tem;
		for (Cliente c : cliente) {
			tem = false;
			if (!listaC.isEmpty()) {

				for (Cliente c2 : listaC) {
					System.out.println(c2.getCodcfo() + " -- " + c.getCodcfo());

					if (c2.getCodcfo().contains(c.getCodcfo())) {
						boolean contem;
						for (int i = 0; i < c.getParcelas().size(); i++) {
							contem = false;
							for (int j = 0; j <  c2.getParcelas().size(); j++) {
								if(c.getParcelas().get(i).getIdlan() == c2.getParcelas().get(j).getIdlan() ) {
									contem = true;
								}
							}
							if (!contem) {
								c2.getParcelas().add(c.getParcelas().get(i));
							}
						}
						
						//c2.setParcelas(c.getParcelas());

						clienteDao.save(c2);
						tem = true;
					}

				}

				if (!tem) {
					clienteDao.save(c);

				}

			} else {
				clienteDao.save(c);
			}

		}

	}

	// ------busca lista de Clientes------------------------------------------

	public List<Cliente> getListClientes() {
		List<Cliente> listaCliente = new ArrayList<>();
		boolean tem;
		for (Cliente c1 : clienteDao.getClientes()) {
			if (!c1.getParcelas().isEmpty()  ) {
			 tem = false;
			 List<Xparcela> xparc = new ArrayList<>();
				for (Xparcela xparcela : c1.getParcelas()) {
					
								
					if (!xparcela.isRenegociada()) {
						c1.setValorS(c1.getValorS() + xparcela.getValor());
						tem =true;
						
					}else {
						xparc.add(xparcela);
						
					}
					
				}
				c1.getParcelas().removeAll(xparc);
				
				if (tem) {
					listaCliente.add(c1);
				}
				

			}
		}

		return listaCliente;

	}

	// -------------------------------gets & Sets-------------------------------
	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

}
