package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;
import com.example.demo.service.ClassificationRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email-classification")
public class ClassificationRuleController {

    @Autowired
    private ClassificationRuleService classificationRuleService;

    @GetMapping("/classify/{accountId}")
    public ResponseEntity<Map<DomainEntity, List<Email>>> classifyEmailsByDomain(@PathVariable Long accountId) {
        Map<DomainEntity, List<Email>> classifiedEmails = classificationRuleService.classifyEmailsByDomain(accountId);
        return ResponseEntity.ok(classifiedEmails);
    }

    @GetMapping("/domain/{domainName}/{accountId}")
    public ResponseEntity<List<Email>> getEmailsByDomain(@PathVariable String domainName, @PathVariable Long accountId) {
        List<Email> emails = classificationRuleService.getEmailsByDomain(domainName, accountId);
        if (emails != null) {
            return new ResponseEntity<>(emails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
