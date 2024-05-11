package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.ArchivingRule;
import com.example.demo.entitys.Email;
import com.example.demo.repository.ArchivingRuleRepository;
import com.example.demo.repository.EmailRepository;

@Service
public class ArchivingRuleServiceImpl implements ArchivingRuleService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ArchivingRuleRepository archivingRuleRepository;

   
    @Override
    @Scheduled(cron = "@monthly")

    //@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
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

   }
    

