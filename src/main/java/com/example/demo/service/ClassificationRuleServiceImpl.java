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
	/*@Autowired
	ClassificationRuleRepository classificationRuleRepository;

	@Override
	public void createClassificationRule(ClassificationRule classificationRule) {
		classificationRuleRepository.save(classificationRule);
	}

	@Override
	public List<ClassificationRule> getAllClassificationRules() {
		
	        return classificationRuleRepository.findAll();
	    
	}

	@Override
	public void deleteClassificationRule(Long ruleId) {
		 ClassificationRule rule = classificationRuleRepository.findById(ruleId)
		            .orElseThrow(() -> new EntityNotFoundException("Classification rule not found with id: " + ruleId));

		        classificationRuleRepository.delete(rule);
		
	}

	@Override
	public void updateClassificationRule(Long ruleId, ClassificationRule updatedRule) {
		ClassificationRule rule = classificationRuleRepository.findById(ruleId)
	            .orElseThrow(() -> new EntityNotFoundException("Classification rule not found with id: " + ruleId));

	       
	        rule.setCategory(updatedRule.getCategory());
	        rule.setAction(updatedRule.getAction());

	        classificationRuleRepository.save(rule);
		
	}*/
	@Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DomainEntityRepository domainEntityRepository;
   // Modification de la méthode classifyEmailsByDomain() pour retourner une carte de type Map<String, List<Email>>
public Map<String, List<Email>> classifyEmailsByDomain() {
    Map<String, List<Email>> classifiedEmails = new HashMap<>();

    // Récupérer tous les e-mails depuis la base de données
    List<Email> allEmails = emailRepository.findAll();

    // Parcourir tous les e-mails
    for (Email email : allEmails) {
        // Extraire le domaine de l'expéditeur de l'e-mail
        String senderDomain = extractDomain(email.getSender());

        // Si le domaine de l'expéditeur n'est pas nul
        if (senderDomain != null) {
            // Récupérer la liste d'e-mails existante pour ce domaine
            List<Email> emailsForDomain = classifiedEmails.getOrDefault(senderDomain, new ArrayList<>());

            // Ajouter l'e-mail à la liste correspondante dans la carte de classification
            emailsForDomain.add(email);
            classifiedEmails.put(senderDomain, emailsForDomain);
        }
    }

    return classifiedEmails;
}

// Méthode pour extraire le domaine d'une adresse e-mail
private String extractDomain(String email) {
    // Extraire le domaine de l'adresse e-mail
    String[] parts = email.split("@");
    return parts.length == 2 ? parts[1] : null;
}

// Utilisation de la carte modifiée dans getEmailsByDomain()
public List<Email> getEmailsByDomain(String domainName) {
    Map<String, List<Email>> classifiedEmails = classifyEmailsByDomain();
    return classifiedEmails.getOrDefault(domainName, null);
}}
