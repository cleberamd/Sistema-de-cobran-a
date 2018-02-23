package com.camd.business;
 
import com.camd.dao.UserDao;
import com.camd.dao.utils.DAOFactory;
 
public class UserBusiness {
 
    private UserDao userDao;      
     
    public UserBusiness() {
        super();
        setUserDao(DAOFactory.createUser());
    }
     
    public boolean existsValidation(String validation) {
        return userDao.existsValidation(validation);
    }
 
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    } 
    
  
}