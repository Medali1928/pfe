package com.example.demo.service;

import java.util.List;

import com.example.demo.entitys.Account;

public interface AccountService {
	
	public Account save(Account emailAccount);
	public Account findById(Long id);
	public List<Account> getAllEmailAccountsFromDatabase();
	public Account updateAccount(Long accountId, Account updatedAccount);
	public void delete(Account account);
	


}
