package com.camd.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.camd.utils.Role;
import com.camd.business.ClienteBusiness;
import com.camd.business.ProfileBusiness;
import com.camd.model.Profile;
import com.camd.utils.security.GenerateMD5;


@ManagedBean(name = "config")
@ViewScoped 
public class Configuracoes {
	private FacesMessage msg;
	private String exibeDiv;
	private List<Profile> listaUser;
	private Profile selectedUser;
	private String nome;
	private String sobreNome;
	private String email;
	private String usuario;
	private String senha;
	private boolean status;
	private boolean admin;
	private List<Profile> profileFiltrado;
	@PostConstruct
	public void init() {
			
		
	}
	
	
	
	//--------------------------Salva Usuario editado!--------------------------------
	public String saveUsuario() {
		selectedUser.setEmail(email);
		selectedUser.setFirstName(nome);
		selectedUser.setLastName(sobreNome);
		selectedUser.getUser().setLogin(usuario);
		selectedUser.getUser().setActive(status);
		if (status) {
			selectedUser.getUser().setValidation(" ");
		}
		if (admin) {
			selectedUser.getUser().getPermissions().add(Role.ROLE_ADMIN.getValue());
		}else {


	if(selectedUser.getUser().getPermissions().contains("ROLE_ADMIN")) {
		selectedUser.getUser().getPermissions().remove("ROLE_ADMIN");
	}
	
	

			
			
		}
		if(senha!= "000000" && !senha.isEmpty() ) {
			selectedUser.getUser().setPassword(GenerateMD5.generate(senha));
		}
		ProfileBusiness pB = new ProfileBusiness();
		pB.save(selectedUser);
		
		msg = new FacesMessage("Usuario Atualizado");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		return " ";
		
		
		
	}
	
	// metodo para atualizar Lista de parcelas e clientes do sistema.
	public void atualizaClientesParcelas() {

		ClienteBusiness clienteBusiness = new ClienteBusiness();
		clienteBusiness.AtualizarParcClie();
		exibeDiv = " ";
		msg = new FacesMessage("Atualizado registros");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	//-------regras para exibir componente na tela de configuração------------
	
	public void register() {
		try {
			listaUser.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		//return "/admin/register.xhtml";
		exibeDiv = "cadastro";
	}
	
	public void usuarios() {
		//return "/admin/register.xhtml";
		listaUsuario();
		exibeDiv = "listarUsuario";
	}
	
	
	//------Lista de usuarios ----------------------------------------------
	public void listaUsuario() {
		
		ProfileBusiness pB = new ProfileBusiness();
		listaUser=pB.listaUsuario();
		
	}

	public String getExibeDiv() {
		return exibeDiv;
	}

	public void setExibeDiv(String exibeDiv) {
		this.exibeDiv = exibeDiv;
	}

	public List<Profile> getListaUser() {
		return listaUser;
	}

	public void setListaUser(List<Profile> listaUser) {
		this.listaUser = listaUser;
	}


	
	
	public Profile getSelectedUser() {
		return selectedUser;
	}





	public void setSelectedUser(Profile selectedUser) {
		this.selectedUser = selectedUser;
	
		nome = selectedUser.getFirstName();
		sobreNome=selectedUser.getLastName();
		email=selectedUser.getEmail();
		usuario=selectedUser.getUser().getLogin();
		senha="000000";
		status=selectedUser.getUser().isActive();
		admin=false;
		for (String s : selectedUser.getUser().getPermissions()) {
			if (s.contains("ROLE_ADMIN")) {
				admin = true;
			}
		}
	}





	public List<Profile> getProfileFiltrado() {
		return profileFiltrado;
	}


	public void setProfileFiltrado(List<Profile> profileFiltrado) {
		this.profileFiltrado = profileFiltrado;
	}


	public String getNome() {
		return nome;
	}





	public void setNome(String nome) {
		this.nome = nome;
	}





	public String getSobreNome() {
		return sobreNome;
	}





	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getUsuario() {
		return usuario;
	}





	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}





	public String getSenha() {
		return senha;
	}





	public void setSenha(String senha) {
		this.senha = senha;
	}



	public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public boolean isAdmin() {
		return admin;
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}




	
	
}
