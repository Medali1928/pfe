package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.demo.entitys.Email;


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
    @GetMapping("/search")
    public List<Email> searchEmails(@RequestParam(required = false) String sender,
                                    @RequestParam(required = false) String subject,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return emailService.searchEmails(sender, subject, startDate, endDate);
    }
    @GetMapping("/search-by-email")
public Email searchByEmail(@RequestParam String email) {
    return emailService.searchByEmail(email);
}

}
