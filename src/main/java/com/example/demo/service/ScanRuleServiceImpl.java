package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ScanRule;
import com.example.demo.repository.ScanRuleRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class ScanRuleServiceImpl implements ScanRuleService{
	/*@Autowired
     ScanRuleRepository scanRuleRepository;


	@Override
	public void defineScanPeriod(Long ruleId, Integer frequency) {
		 ScanRule scanRule = scanRuleRepository.findById(ruleId)
		            .orElseThrow(() -> new EntityNotFoundException("Scan rule not found with id: " + ruleId));

		        scanRule.setFrequency(frequency);
		        scanRuleRepository.save(scanRule);
		
	}

	@Override
	public void defineEmailAccountToScan(Long ruleId, Account emailAccount) {
		ScanRule scanRule = scanRuleRepository.findById(ruleId)
	            .orElseThrow(() -> new EntityNotFoundException("Scan rule not found with id: " + ruleId));

	        scanRule.setAccount(emailAccount);
	        scanRuleRepository.save(scanRule);
	}

	@Override
	public void createScanRule(ScanRule scanrule) {
		scanRuleRepository.save(scanrule);
		
	}*/
	


    @Autowired
    private EmailService1 emailService;

    @Autowired
    private ScanRuleRepository scanRuleRepository;

    // Méthode de scan des e-mails planifiée
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Exécuter toutes les 24 heures
    
    public void scanEmails() {
        // Récupérer toutes les règles de scan à partir du ScanRuleRepository
        Iterable<ScanRule> scanRules = scanRuleRepository.findAll();

        // Parcourir chaque règle et exécuter la récupération des e-mails avec la fréquence correspondante
        for (ScanRule scanRule : scanRules) {
            // Récupérer la fréquence de scan
            String frequency = scanRule.getFrequency();

            // Exécuter la récupération des e-mails avec la fréquence spécifiée
            if ("daily".equalsIgnoreCase(frequency)) {
                // Planifier la tâche de scan pour s'exécuter une fois par jour
                emailService.fetchAndSaveEmails();
            } else if ("hourly".equalsIgnoreCase(frequency)) {
                // Planifier la tâche de scan pour s'exécuter une fois par heure
                // (vous devez ajuster cette planification en fonction de votre configuration)
            } else if ("weekly".equalsIgnoreCase(frequency)) {
                // Planifier la tâche de scan pour s'exécuter une fois par semaine
                // (vous devez ajuster cette planification en fonction de votre configuration)
            }
            // Ajoutez des conditions supplémentaires pour d'autres fréquences si nécessaire
        }
    }
}




