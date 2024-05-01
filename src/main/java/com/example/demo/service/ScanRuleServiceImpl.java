package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ScanRule;
import com.example.demo.repository.ScanRuleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ScanRuleServiceImpl implements ScanRuleService{
	@Autowired
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
		
	}

}

