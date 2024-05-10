package com.example.demo.service;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ScanRule;

public interface ScanRuleService {
	//public void createScanRule(ScanRule scanrule);
	// public void defineScanPeriod(Long ruleId, Integer frequency);
	// public void defineEmailAccountToScan(Long ruleId, Account emailAccount);
	public void scanEmails();

}
