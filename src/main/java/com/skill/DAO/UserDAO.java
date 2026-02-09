package com.skill.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skill.entity.UserDetails;
import com.skill.repository.UserRepository;
@Repository
public class UserDAO {
	@Autowired
	UserRepository repository;
	
	public UserDetails insertData(UserDetails details){
		return repository.save(details);
	}
	
	public List<UserDetails> getAllUsers() {
		return repository.findAll();
	}
	
	public UserDetails getUser(String email,String password) {
		return repository.findByEmailidAndPassword(email, password);
	}
	
	public UserDetails updateData(UserDetails details) {
		return repository.save(details);
	}
	
	public int deleteUser(String email) {
		return repository.removeByEmailid(email);
	}
	
	public Optional<UserDetails> getUser(String email) {
		return repository.findById(email);
	}
	
}
