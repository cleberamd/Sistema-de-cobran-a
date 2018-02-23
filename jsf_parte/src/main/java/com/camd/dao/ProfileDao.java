package com.camd.dao;

import java.util.List;

import com.camd.model.Profile;

public interface ProfileDao {
	
	public void save(Profile profile);

	public List<Profile> getListaUser();

}
