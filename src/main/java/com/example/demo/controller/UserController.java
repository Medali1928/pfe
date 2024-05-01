package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
@Autowired
	UserService userserv;
	@GetMapping("/users")
	public List<User> getAllUsers() {
		List<User> users=userserv.getAllUsers();
		return users;
		}
	
	@PostMapping("/addUser")
	public User addUser(@RequestBody User usr) {
		return userserv.addUser(usr);
	}
	@DeleteMapping("deleteuser/{iduser}")
	public String deleteuser(@PathVariable Long iduser) {
		return userserv.deleteUser(iduser);
	}
	@PutMapping("updateUser/{id}")
	public User UpdateUser(@RequestBody User user,@PathVariable Long id) {
		return userserv.UpdateUser(user, id);
	}


}
