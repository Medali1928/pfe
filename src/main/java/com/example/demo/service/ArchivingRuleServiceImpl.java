package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void archiverEmailsSelonRegles() {
        List<ArchivingRule> regles = archivingRuleRepository.findAll();
        for (ArchivingRule regle : regles) {
            // Calcul de la date limite (1 an dans le passé par rapport à la date actuelle)
            LocalDate dateLimite = LocalDate.now().minus(1, ChronoUnit.YEARS);
            List<Email> emailsAarchiver = emailRepository.findByDateBeforeAndArchivedFalse(dateLimite);
            for (Email email : emailsAarchiver) {
                // Archivage de l'email
                email.setArchived(true);
                emailRepository.save(email);
            }
        }
    }
}
