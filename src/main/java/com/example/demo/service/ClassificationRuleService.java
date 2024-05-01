package com.example.demo.service;

import java.util.List;

import com.example.demo.entitys.ClassificationRule;

public interface ClassificationRuleService {
	public void createClassificationRule(ClassificationRule classificationRule);
	public List<ClassificationRule> getAllClassificationRules();
	 public void deleteClassificationRule(Long ruleId);
	 public void updateClassificationRule(Long ruleId, ClassificationRule updatedRule);

}
