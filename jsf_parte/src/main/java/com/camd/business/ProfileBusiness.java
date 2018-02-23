package com.camd.business;

import java.util.List;

import com.camd.dao.ProfileDao;
import com.camd.dao.utils.DAOFactory;
import com.camd.model.Profile;
 
public class ProfileBusiness {
 
    private ProfileDao profileDao;
     
     
    public ProfileBusiness() {
        super();
        setProfileDao(DAOFactory.createProfile());
    }    
    public List<Profile> listaUsuario(){
     return profileDao.getListaUser();
      	
      }
     
    private void setProfileDao(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }
 
    public void save(Profile Profile){
        profileDao.save(Profile);
    }    
}