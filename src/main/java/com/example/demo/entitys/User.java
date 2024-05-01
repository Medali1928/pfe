package com.example.demo.entitys;




import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_user;
	private String userName;
	private String password;
	private String Email;
	//hi
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole role;
	@OneToMany(mappedBy = "user")
    private List<Account> accounts;
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public void setEmail(String email) {
		Email = email;
	}
	public ERole getRole() {
		return role;
	}
	public void setRole(ERole role) {
		this.role = role;
	}
	public User(Long id_user, String userName, String password, String email, ERole role, List<Account> accounts) {
		super();
		this.id_user = id_user;
		this.userName = userName;
		this.password = password;
		Email = email;
		this.role = role;
		this.accounts = accounts;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
