package com.camd.dao;
 
import com.camd.model.User;
 
public interface UserDao {
     public boolean existsValidation(String validation);
     public void save(User user);
	
}