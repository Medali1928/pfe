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

    @GetMapping("/all")
public ResponseEntity<List<Account>> getAllEmailAccounts() {
    List<Account> emailAccounts =  accountserv.getAllEmailAccountsFromDatabase();
    return ResponseEntity.ok(emailAccounts);
}
@PutMapping("/update/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        Account existingAccount = accountserv.findById(id);
        if (existingAccount != null) {
            // Mettre à jour les champs du compte existant avec les nouvelles valeurs
            existingAccount.setPassword(updatedAccount.getPassword());
            existingAccount.setEmail(updatedAccount.getEmail());
            existingAccount.setPort(updatedAccount.getPort());
            existingAccount.setServeur(updatedAccount.getServeur());
            // Sauvegarder le compte mis à jour
            Account updatedAccountResult = accountserv.save(existingAccount);
            return ResponseEntity.ok(updatedAccountResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        Account existingAccount = accountserv.findById(id);
        if (existingAccount != null) {
            accountserv.delete(existingAccount);
            return ResponseEntity.ok("Account with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

