package com.camd.controller.initial;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@RequestScoped
@ManagedBean(name = "inicio")
public class Index {

	@PostConstruct
	public void init() {
		System.out.println("Bean executado!");
	}
	
	public void sessaoFinalizada() {
		
		System.out.println("executei ----");
		
		FacesContext conext = FacesContext.getCurrentInstance();
        //Verifica a sessao e a grava na variavel
        HttpSession session = (HttpSession) conext.getExternalContext().getSession(false);
        //Fecha/Destroi sessao
        session.invalidate();
        FacesMessage msg = new FacesMessage("Sua sessão expirou!!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	
	public String login(){
        return "/public/login.xhtml";
    }
	
	public String index(){
         return "/public/index.xhtml";
	}

	public String getMessage() {
		return "Hello World JSF!";
	}


}
