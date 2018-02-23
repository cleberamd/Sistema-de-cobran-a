package com.camd.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.camd.model.Profile;
import com.camd.model.User;
 
public class ProfileDaoImpl implements ProfileDao {
 
    private Session session;
 
    public void setSession(Session session) {
        this.session = session;
    }
     
    @Override
    public void save(Profile profile) {
        this.session.saveOrUpdate(profile);
    }
    
	@Override
	public List<Profile> getListaUser() {
		 Criteria criteria = session.createCriteria(Profile.class);
		 List<Profile> listaUser = criteria.list();
		 for (Profile profile : listaUser) {
			 Hibernate.initialize(profile.getUser().getPermissions());
		}
		return listaUser;
	}
}
