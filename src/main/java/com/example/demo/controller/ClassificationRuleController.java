package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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


import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;
import com.example.demo.service.ClassificationRuleService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/email-classification")
public class ClassificationRuleController {
	 @Autowired
	    ClassificationRuleService classificationRuleService;
    @GetMapping("/classify")
    public ResponseEntity<Map<DomainEntity, List<Email>>> classifyEmailsByDomain() {
        Map<DomainEntity, List<Email>> classifiedEmails = classificationRuleService.classifyEmailsByDomain();
        return ResponseEntity.ok(classifiedEmails);
    }
	
@GetMapping("/domain/{domainName}")
public ResponseEntity<List<Email>> getEmailsByDomain(@PathVariable String domainName) {
	List<Email> emails = classificationRuleService.getEmailsByDomain(domainName);
	if (emails != null) {
		return new ResponseEntity<>(emails, HttpStatus.OK);
	} else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}


}

