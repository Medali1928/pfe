package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ArchivingRuleService;

@RestController
public class ArchivingRuleController {
	 @Autowired
	    private ArchivingRuleService archivingService;

	    @PostMapping("/archiver-emails")
	    public void archiverEmailsSelonRegles() {
	        archivingService.archiverEmailsSelonRegles();
	    }

}