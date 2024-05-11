package com.example.demo.service;

import com.example.demo.entitys.Account;

public interface AccountService {
	//public Account addCompte( Account account);
	//public String DeleteCompte(Long account_id);
	//public Account updateAccount(Long account_Id, Account account);
	public Account save(Account emailAccount);
	public Account findById(Long id);
	


}
