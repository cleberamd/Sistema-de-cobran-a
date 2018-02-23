package com.camd.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.camd.business.ProfileBusiness;
import com.camd.model.Profile;
import com.camd.model.User;
import com.camd.utils.GenerateValidation;
import com.camd.utils.Role;
import com.camd.utils.security.GenerateMD5;

@ManagedBean(name = "user")
@RequestScoped
public class UserBean {

	private User user = null;
	private Profile profile = null;
	private FacesMessage msg;
/*
	private int day = 0;
	private int month = 0;
	private int year = 0;
*/
	@PostConstruct
	public void init() {
		user = new User();
		profile = new Profile();
	}
/* Buscar usuario Logado!
FacesContext context = FacesContext.getCurrentInstance();
ExternalContext external = context.getExternalContext();
String login = external.getRemoteUser();
*/

	
	public String save() {

		ProfileBusiness profileBusiness = new ProfileBusiness();
        user.setPassword(GenerateMD5.generate(user.getPassword()));
        user.setValidation(GenerateValidation.keyValidation());
        user.getPermissions().add(Role.ROLE_COMMON.getValue());
        user.setActive(false);
        
        profile.setUser(user);
        //profile.setBirth(ManipulateDate.getDate(year, month, day));
        try {
        	 profileBusiness.save(profile);
        	 msg = new FacesMessage("Usuario Salvo"," ");
		} catch (Exception e) {
			 msg = new FacesMessage(FacesMessage.SEVERITY_WARN,"ERRO AO SALVAR!","Verifique se o usuario já existe");
				
		}
       
        
      
      //  SimpleRegistrationService mail = (SimpleRegistrationService) ServiceFinder.findBean("registrationService");
    //    mail.register(profile);
       
		FacesContext.getCurrentInstance().addMessage(null, msg);
        return " ";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	

	/*	public Map<String, Object> getDays() {
		return ManipulateDate.getDays();
	}

	public Map<String, Object> getMonths() {
		return ManipulateDate.getMonths();
	}

	public Map<String, Object> getYears() {
		return ManipulateDate.getYears();
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}*/
}
