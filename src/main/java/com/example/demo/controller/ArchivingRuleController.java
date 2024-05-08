package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.Email;
import com.example.demo.service.ArchivingRuleService;

@RestController
public class ArchivingRuleController {
	 @Autowired
	    private ArchivingRuleService archivingService;

		@PostMapping("/archiver/emails")
		public ResponseEntity<String> archiverEmailsSelonRegles() {
			archivingService.archiverEmailsSelonRegles();
			return new ResponseEntity<>("Archivage des e-mails déclenché avec succès.", HttpStatus.OK);
		}
		 
		

}