package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.repository.AccountRepository;

@Service

public class AccountServiceImpl implements AccountService {
	

/* 	@Override
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
	}*/
	@Autowired
    private AccountRepository emailAccountRepository;

    public Account save(Account emailAccount) {
        return emailAccountRepository.save(emailAccount);
    }

    public Account findById(Long id) {
        return emailAccountRepository.findById(id).orElse(null);
    }
	public List<Account> getAllEmailAccountsFromDatabase() {
        return emailAccountRepository.findAll(); // Utilisez la méthode appropriée de votre repository pour récupérer tous les comptes
    }
	
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Optional<Account> optionalAccount = emailAccountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();
            existingAccount.setPassword(updatedAccount.getPassword());
            existingAccount.setEmail(updatedAccount.getEmail());
            existingAccount.setPort(updatedAccount.getPort());
            existingAccount.setServeur(updatedAccount.getServeur());
            // Sauvegarder le compte mis à jour dans la base de données
            return emailAccountRepository.save(existingAccount);
        } else {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
    }
	public void delete(Account account) {
		emailAccountRepository.delete(account);
    }

	}


