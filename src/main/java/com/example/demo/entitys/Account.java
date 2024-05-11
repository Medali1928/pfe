package com.example.demo.entitys;

import java.util.List;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long account_id ;
	private String password;
	private String Email;
	
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
	public Account(Long account_id, String password, String Email, String port, String serveur) {
		super();
		this.account_id = account_id;
		this.password = password;
		this.Email = Email;
		
		this.port = port;
		this.serveur = serveur;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
