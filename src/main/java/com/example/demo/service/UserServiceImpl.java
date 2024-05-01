package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepo;
	@Override
	public User addUser(User usr) {
		// TODO Auto-generated method stub
		return userRepo.save(usr);
	}
	@Override
	public String deleteUser(Long iduser) {
	User usr=userRepo.findById(iduser).get();
	String ch="";
	if(usr!=null) {
		userRepo.deleteById(iduser);
		ch="user deleted";
	}else {ch="id user not found ";
	}
	return ch;}
	@Override
	public User UpdateUser(User user, Long id) {
		User usr=userRepo.findById(id).get();
		if(usr != null) {
			usr.setUserName(user.getUserName());
			usr.setPassword(user.getPassword());
			usr.setEmail(user.getEmail());
		}
		return userRepo.save(usr);
	}
	@Override
	public List<User> getAllUsers() {
		List<User> users=userRepo.findAll();
		return users;
	}
}
