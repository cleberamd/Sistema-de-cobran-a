package com.camd.dao.utils;





import com.camd.dao.ClienteDao;
import com.camd.dao.ClienteImpl;
import com.camd.dao.ProfileDao;
import com.camd.dao.ProfileDaoImpl;
import com.camd.dao.UserDao;
import com.camd.dao.UserImpl;
import com.camd.dao.XparcelaDao;
import com.camd.dao.XparcelaImpl;

 
public class DAOFactory {
 
    public static ProfileDao createProfile(){
        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl();
        profileDaoImpl.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return profileDaoImpl;
    }
    
    public static UserDao createUser(){
        UserImpl userImpl = new UserImpl();
        userImpl.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return userImpl;
    }
    
    public static XparcelaDao createXparcela(){
        XparcelaImpl xparcelaImpl = new XparcelaImpl();
        xparcelaImpl.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return xparcelaImpl;
    }

    public static ClienteDao createCliente(){
    	ClienteImpl clienteImpl = new ClienteImpl();
    	clienteImpl.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return clienteImpl;
    }
    
 
}