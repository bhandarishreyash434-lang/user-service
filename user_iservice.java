package com.user.service;

import java.util.List;

import com.user.model.user_model;

public interface user_iservice {
	public user_model saveuser(user_model user);
	 
	 public List<user_model>getalluser();
	 
	 public user_model getuser(String userId);
	 
	 public user_model updatebyid(String userId,user_model userupdate) ;
	 
	 public String deletebyId(String userId) ;
	 
}
