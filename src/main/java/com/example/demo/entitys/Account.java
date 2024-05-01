package com.example.demo.entitys;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Account {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long account_id ;
	private String password;
	private String Email;
	private String role;
	private String port;
	private String serveur;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne
    private User user;
	 
	   @OneToMany(mappedBy = "account")
	    private List<Rule> rules;
	public Long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String Email) {
		this.Email = Email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getServeur() {
		return serveur;
	}
	public void setServeur(String serveur) {
		this.serveur = serveur;
	}
	public Account(Long account_id, String password, String Email, String role, String port, String serveur) {
		super();
		this.account_id = account_id;
		this.password = password;
		this.Email = Email;
		this.role = role;
		this.port = port;
		this.serveur = serveur;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
