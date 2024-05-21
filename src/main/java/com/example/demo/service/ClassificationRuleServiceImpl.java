package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;
import com.example.demo.repository.DomainEntityRepository;
import com.example.demo.repository.EmailRepository;

@Service
public class ClassificationRuleServiceImpl implements ClassificationRuleService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DomainEntityRepository domainEntityRepository;

    public Map<DomainEntity, List<Email>> classifyEmailsByDomain(Long accountId) {
        Map<DomainEntity, List<Email>> classifiedEmails = new HashMap<>();

        // Retrieve emails for the given account ID from the database
        List<Email> accountEmails = emailRepository.findByAccountId(accountId);

        // Iterate over the emails for the account
        for (Email email : accountEmails) {
            // Extract the domain of the sender's email
            String senderDomain = extractDomain(email.getSender());

            // If the sender's domain is not null
            if (senderDomain != null) {
                // Retrieve the domain entity corresponding to the sender's domain from the database
                DomainEntity domainEntity = domainEntityRepository.findByDomainName(senderDomain);

                // If the corresponding domain entity does not exist
                if (domainEntity == null) {
                    // Create a new DomainEntity for the domain
                    domainEntity = new DomainEntity();
                    domainEntity.setDomainName(senderDomain);
                    domainEntity = domainEntityRepository.save(domainEntity);
                }

                // Add the email to the corresponding list in the classification map
                List<Email> emailsForDomain = classifiedEmails.getOrDefault(domainEntity, new ArrayList<>());
                emailsForDomain.add(email);
                classifiedEmails.put(domainEntity, emailsForDomain);
            }
        }

        return classifiedEmails;
    }

    private String extractDomain(String email) {
        // Extract the domain from the email address
        String[] parts = email.split("@");
        return parts.length == 2 ? parts[1] : null;
    }

    public List<Email> getEmailsByDomain(String domainName, Long accountId) {
        // Retrieve emails classified by domain for the given account ID
        Map<DomainEntity, List<Email>> classifiedEmails = classifyEmailsByDomain(accountId);

        // Iterate over the map of emails classified by domain
        for (Map.Entry<DomainEntity, List<Email>> entry : classifiedEmails.entrySet()) {
            DomainEntity domainEntity = entry.getKey();
            // Check if the domain name matches the one being searched for
            if (domainEntity.getDomainName().equals(domainName)) {
                // Return the list of emails associated with this domain
                return entry.getValue();
            }
        }
        // Return null if no emails are found for this domain
        return null;
    }
}
