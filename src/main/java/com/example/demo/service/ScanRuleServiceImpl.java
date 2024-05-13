package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ScanRule;
import com.example.demo.repository.ScanRuleRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityNotFoundException;
@EnableScheduling
@Service
public class ScanRuleServiceImpl implements ScanRuleService {

    @Autowired
    ScanRuleRepository scanRuleRepository;

    @Autowired
    private EmailService1 emailService;
    @Autowired
    private AccountService emailAccountService;

    //@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Scheduled(cron = " 1 18 19 * * *")
    public void scanEmails() {
        // Récupérer tous les comptes de messagerie de la base de données
        List<Account> emailAccounts = emailAccountService.getAllEmailAccountsFromDatabase();
        
        // Itérer sur chaque compte et appeler la méthode de récupération d'e-mails
        for (Account emailAccount : emailAccounts) {
            emailService.fetchAndSaveEmails(emailAccount.getAccount_id());
        }
    }
}
	/*@Override
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
	


   // @Autowired
    //private EmailService1 emailService;

    //@Autowired
    //private ScanRuleRepository scanRuleRepository;

   

    // Méthode de scan des e-mails planifiée
   //@Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Exécuter toutes les 24 heures
   /*@Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
   public void scanEmails() {
       // Récupérer toutes les règles de scan à partir du ScanRuleRepository
       Iterable<ScanRule> scanRules = scanRuleRepository.findAll();

       // Parcourir chaque règle et exécuter la récupération des e-mails avec la fréquence correspondante
       for (ScanRule scanRule : scanRules) {
           // Récupérer la fréquence de scan
           String frequency = scanRule.getFrequency();

           // Exécuter la récupération des e-mails avec la fréquence spécifiée
           if ("daily".equalsIgnoreCase(frequency) || "hourly".equalsIgnoreCase(frequency) || "weekly".equalsIgnoreCase(frequency)) {
               // Appeler la méthode de récupération des e-mails de EmailService1
               emailService.fetchAndSaveEmails(scanRule.getAccountId());
           }
           // Ajoutez des conditions supplémentaires pour d'autres fréquences si nécessaire
       }
   }} */
  

    
  






