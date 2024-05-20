package com.example.demo.service;

import java.util.List;
import java.util.Map;


import com.example.demo.entitys.DomainEntity;
import com.example.demo.entitys.Email;

public interface ClassificationRuleService {
	//public void createClassificationRule(ClassificationRule classificationRule);
	//public List<ClassificationRule> getAllClassificationRules();
	 //public void deleteClassificationRule(Long ruleId);
	 //public void updateClassificationRule(Long ruleId, ClassificationRule updatedRule);
	//  public Map<String, List<Email>> classifyEmailsByDomain();
	 //public List<Email> getEmailsByDomain(String domainName);
	  public Map<DomainEntity, List<Email>> classifyEmailsByDomain() ;
	  public List<Email> getEmailsByDomain(String domainName);
}
