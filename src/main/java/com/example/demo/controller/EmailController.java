package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.service.EmailService1;
@RestController
@RequestMapping("/api/emails")
public class EmailController {
    @Autowired
    private EmailService1 emailService;

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveEmails() {
        emailService.fetchAndSaveEmails();
        return ResponseEntity.ok("Emails fetched and saved successfully.");
    }

}
