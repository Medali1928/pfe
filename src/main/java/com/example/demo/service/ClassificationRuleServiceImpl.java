package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.ClassificationRule;
import com.example.demo.repository.ClassificationRuleRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class ClassificationRuleServiceImpl implements ClassificationRuleService {
	@Autowired
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
		
	}

}
