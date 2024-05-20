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

import com.example.demo.entitys.ArchivedEmail;

import com.example.demo.service.ArchivedEmailService;

@RestController
@RequestMapping("/api/v1/auth/emails")
public class ArchivedEmailController {
     @Autowired
    private ArchivedEmailService archivedEmailService;

    @PostMapping("/archiver/emails")
    public ResponseEntity<String> archiverEmailsSelonRegles() {
        archivedEmailService.archiverEmailsSelonRegles();
        return new ResponseEntity<>("Archivage des e-mails déclenché avec succès.", HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<ArchivedEmail> getAllArchivedEmails(@RequestParam("accountId") Long accountId) {
        return archivedEmailService.getAllArchivedEmails(accountId);
    }

    @GetMapping("/archived-emails")
    public List<ArchivedEmail> getArchivedEmailsByDateRange(@RequestParam("accountId") Long accountId,
                                                            @RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return archivedEmailService.getArchivedEmailsByDateRange(accountId, start, end);
    }

    @GetMapping("/archived-emails-by-sender")
    public List<ArchivedEmail> getArchivedEmailsBySenderAndDateRange(@RequestParam("accountId") Long accountId,
                                                                     @RequestParam("sender") String sender,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return archivedEmailService.getArchivedEmailsBySenderAndDateRange(accountId, sender, start, end);
    }

    @DeleteMapping("/archived-emails/{accountId}/{id}")
    public ResponseEntity<String> deleteArchivedEmail(@PathVariable Long accountId, @PathVariable Long id) {
       ArchivedEmail archivedEmail = archivedEmailService.getArchivedEmailById(accountId, id);
        if (archivedEmail != null) {
            archivedEmailService.deleteArchivedEmail(accountId,archivedEmail);
            return new ResponseEntity<>("E-mail archivé supprimé avec succès.", HttpStatus.OK);
        }
        return new ResponseEntity<>("E-mail archivé non trouvé.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/archived-emails/delete-old")
    public ResponseEntity<String> deleteArchivedEmailsOlderThanOneYear(@RequestParam("accountId") Long accountId) {
        archivedEmailService.deleteArchivedEmailsOlderThanOneYear(accountId);
        return new ResponseEntity<>("E-mails archivés de plus d'un an supprimés avec succès.", HttpStatus.OK);
    }

}
