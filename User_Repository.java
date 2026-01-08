package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.model.user_model;

public interface User_Repository extends JpaRepository<user_model,String>{

}
