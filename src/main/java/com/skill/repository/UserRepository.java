package com.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skill.entity.UserDetails;

import jakarta.transaction.Transactional;


public interface UserRepository extends JpaRepository<UserDetails, String> {
	@Transactional
	int removeByEmailid(String emailid);
	
	UserDetails findByEmailidAndPassword(String emailid,String password);
}