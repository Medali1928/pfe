package com.example.demo.controller;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.Email;
import com.example.demo.service.AccountService;
import com.example.demo.service.ReceiveMailService;
 
@RestController
@RequestMapping("account")
public class AccountController {
	@Autowired
	AccountService accountserv;

	
@PostMapping("/addCompte")
public Account addCompte(@RequestBody Account account) {
		
		return accountserv.addCompte(account);
	}


	

		
	@DeleteMapping("deletecompte/{account_id}")
public String deleteuser(@PathVariable Long account_id) {
	return accountserv.DeleteCompte(account_id);
}


@PutMapping("updateaccount/{account_id}")
public Account  updateaccount(@PathVariable Long account_id,@RequestBody Account a) {
	return accountserv.updateAccount(account_id, a);
 
}}

