package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entitys.ArchivingRule;
import com.example.demo.entitys.Email;
import com.example.demo.repository.ArchivingRuleRepository;
import com.example.demo.repository.EmailRepository;
@EnableScheduling
@Service
public class ArchivingRuleServiceImpl implements ArchivingRuleService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ArchivingRuleRepository archivingRuleRepository;

   
    @Override
   

    @Scheduled(cron = "@monthly")
    public void archiverEmailsSelonRegles() {
    List<Email> regles = emailRepository.findAll();
    for (Email regle : regles) {
        // Vérifier si la règle est active
   
            LocalDate dateLimite = LocalDate.now().minus(1, ChronoUnit.YEARS);
            // Récupérer tous les e-mails à archiver avant la date limite
            List<Email> emailsAarchiver = emailRepository.findByDateBefore(dateLimite);
            // Archiver les e-mails récupérés
            for (Email email : emailsAarchiver) {
                System.out.print(email);
                // Archiver l'e-mail en utilisant les informations de la règle
               // archivingRuleRepository.save(email.getBody(), email.getRecipients(), email.getAttachments(), email.getSubject(), email.getDate(), email.getSender());
                // Marquer l'e-mail comme archivé
                //email.setArchived(true);

                ArchivingRule  ar = new ArchivingRule(email.getSender(), email.getRecipients(),email.getSubject(),email.getBody(), email.getDate(), email.getAttachments());
                archivingRuleRepository.save(ar);
                emailRepository.delete(email);
            }
        }
    }
    public List<ArchivingRule> getAllArchivedEmails() {
        // Récupérer tous les e-mails archivés à partir du repository
        return archivingRuleRepository.findAll();
    }
    public List<ArchivingRule> getArchivedEmailsByDateRange(LocalDate startDate, LocalDate endDate) {
        return archivingRuleRepository.findByDateBetween(startDate, endDate);
    }
    public List<ArchivingRule> getArchivedEmailsBySenderAndDateRange(String sender, LocalDate startDate, LocalDate endDate) {
        return archivingRuleRepository.findBySenderAndDateBetween(sender, startDate, endDate);
    }
    public ArchivingRule getArchivedEmailById(Long id) {
        return archivingRuleRepository.findById(id).orElse(null);
    }

    public void deleteArchivedEmail(ArchivingRule archivingRule) {
        archivingRuleRepository.delete(archivingRule);
    }
    @Transactional
    public void deleteArchivedEmailsOlderThanOneYear() {
        // Calculer la date d'un an auparavant
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        // Supprimer les emails archivés qui ont plus d'un an
        archivingRuleRepository.deleteAllByDateBefore(oneYearAgo);
    }
   }
    

