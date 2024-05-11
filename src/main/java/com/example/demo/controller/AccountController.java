package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

 
@RestController
@RequestMapping("/api/v1/auth/")
public class AccountController {
	@Autowired
	AccountService accountserv;

/*	
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
 
}*/
    @PostMapping("/create")
    public ResponseEntity<Account> createEmailAccount(@RequestBody Account emailAccount) {
        Account savedEmailAccount =  accountserv.save(emailAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmailAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getEmailAccountById(@PathVariable Long id) {
       Account emailAccount =  accountserv.findById(id);
        if (emailAccount != null) {
            return ResponseEntity.ok(emailAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   /*  @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllEmailAccounts() {
        List<Account> emailAccounts =  accountserv.getAll();
        return ResponseEntity.ok(emailAccounts);
    }*/


}

