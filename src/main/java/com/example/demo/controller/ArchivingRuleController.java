package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.ArchivingRule;
import com.example.demo.entitys.Email;
import com.example.demo.service.ArchivingRuleService;

@RestController
@RequestMapping("/api/v1/auth/emails")
public class ArchivingRuleController {
	 @Autowired
	    private ArchivingRuleService archivingService;

		@PostMapping("/archiver/emails")
		public ResponseEntity<String> archiverEmailsSelonRegles() {
			archivingService.archiverEmailsSelonRegles();
			return new ResponseEntity<>("Archivage des e-mails déclenché avec succès.", HttpStatus.OK);
		}
		@GetMapping("/All")
    public List<ArchivingRule> getAllArchivedEmails() {
        // Appeler le service pour récupérer tous les e-mails archivés
        return archivingService.getAllArchivedEmails();
    }
	@GetMapping("/archived-emails")
    public List<ArchivingRule> getArchivedEmailsByDateRange(@RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return archivingService.getArchivedEmailsByDateRange(start, end);
    }
	@GetMapping("/archived-emails-by-sender")
	public List<ArchivingRule> getArchivedEmailsBySenderAndDateRange(@RequestParam("sender") String sender,
																	 @RequestParam("startDate") String startDate,
																	 @RequestParam("endDate") String endDate) {
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		return archivingService.getArchivedEmailsBySenderAndDateRange(sender, start, end);
	}
	@DeleteMapping("/archived-emails/{id}")
    public void deleteArchivedEmail(@PathVariable Long id) {
        ArchivingRule archivingRule = archivingService.getArchivedEmailById(id);
        if (archivingRule != null) {
            archivingService.deleteArchivedEmail(archivingRule);
        }
    }
	@DeleteMapping("/archived-emails/delete-old")
    public void deleteArchivedEmailsOlderThanOneYear() {
        archivingService.deleteArchivedEmailsOlderThanOneYear();
    }
	
		

}