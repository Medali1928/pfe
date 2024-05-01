package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.repository.AccountRepository;

@Service

public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepository accountRepo;

	@Override
	public Account addCompte(Account account) {
		
		return accountRepo.save(account);
	}

	@Override
	public String DeleteCompte(Long account_id) {
		Account cpt=accountRepo.findById(account_id).get();
		String ch="";
		if(cpt!=null) {
			accountRepo.deleteById(account_id);
			ch="account deleted";
		}else {ch="id account not found ";
		}
		return ch;}

	@Override
	public Account updateAccount(Long account_Id, Account account) {
		Account cpt = accountRepo.findById(account_Id).get();
		 if(cpt != null) {
			 cpt.setEmail(account.getEmail());
			 cpt.setPassword(account.getPassword());
			 cpt .setRole(account.getRole());
			 cpt.setPort(account.getPort());
			 cpt.setServeur(account.getServeur());
	     
		 }
		return accountRepo.save(cpt);
	}
	}


