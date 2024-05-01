package com.example.demo.service;

import java.util.List;

import com.example.demo.entitys.User;

public interface UserService {
	public User addUser(User usr);
	public String deleteUser(Long iduser);
	public User UpdateUser(User user,Long id);
	public List<User> getAllUsers();


}
