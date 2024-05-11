package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.ClassificationRule;
import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;
import com.example.demo.repository.ClassificationRuleRepository;
import com.example.demo.repository.DomainEntityRepository;
import com.example.demo.repository.EmailRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class ClassificationRuleServiceImpl implements ClassificationRuleService {
	@Autowired
	ClassificationRuleRepository classificationRuleRepository;

	

@Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DomainEntityRepository domainEntityRepository;
    public Map<DomainEntity, List<Email>> classifyEmailsByDomain() {
        Map<DomainEntity, List<Email>> classifiedEmails = new HashMap<>();
    
        // Récupérer tous les e-mails depuis la base de données
        List<Email> allEmails = emailRepository.findAll();
    
        // Parcourir tous les e-mails
        for (Email email : allEmails) {
            // Extraire le domaine de l'expéditeur de l'e-mail
            String senderDomain = extractDomain(email.getSender());
    
            // Si le domaine de l'expéditeur n'est pas nul
            if (senderDomain != null) {
                // Récupérer l'entité de domaine correspondant au domaine de l'expéditeur depuis la base de données
                DomainEntity domainEntity = domainEntityRepository.findByDomainName(senderDomain);
    
                // Si l'entité de domaine correspondant n'existe pas
                if (domainEntity == null) {
                    // Créer une nouvelle entité DomainEntity pour le domaine
                    domainEntity = new DomainEntity();
                    domainEntity.setDomainName(senderDomain);
                    domainEntity = domainEntityRepository.save(domainEntity);
                }
    
                // Ajouter l'e-mail à la liste correspondante dans la carte de classification
                List<Email> emailsForDomain = classifiedEmails.getOrDefault(domainEntity, new ArrayList<>());
                emailsForDomain.add(email);
                classifiedEmails.put(domainEntity, emailsForDomain);
            }
        }
    
        return classifiedEmails;
    }

    private String extractDomain(String email) {
        // Extraire le domaine de l'adresse e-mail
        String[] parts = email.split("@");
        return parts.length == 2 ? parts[1] : null;
    }
   public List<Email> getEmailsByDomain(String domainName) {
    // Récupérer tous les e-mails classifiés par domaine
    Map<DomainEntity, List<Email>> classifiedEmails = classifyEmailsByDomain();
    
    // Parcourir la carte des e-mails classifiés par domaine
    for (Map.Entry<DomainEntity, List<Email>> entry : classifiedEmails.entrySet()) {
        DomainEntity domainEntity = entry.getKey();
        // Vérifier si le nom de domaine correspond à celui recherché
        if (domainEntity.getDomainName().equals(domainName)) {
            // Retourner la liste d'e-mails associée à ce domaine
            return entry.getValue();
        }
    }
    // Retourner null si aucun e-mail n'est trouvé pour ce domaine
    return null;
}

    
  
}


