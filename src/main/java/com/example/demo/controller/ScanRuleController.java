package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.Account;
import com.example.demo.entitys.ScanRule;
import com.example.demo.service.ScanRuleService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/scan-rules")
public class ScanRuleController {
	/*@Autowired
    ScanRuleService scanRuleService;

    @PostMapping("/{ruleId}/define-scan-period")
    public ResponseEntity<String> defineScanPeriod(@PathVariable("ruleId") Long ruleId, @RequestBody Integer frequency) {
        try {
            scanRuleService.defineScanPeriod(ruleId, frequency);
            return ResponseEntity.ok("Scan period defined successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while defining the scan period.");
        }

}
    @PostMapping("/{ruleId}/define-email-account")
    public ResponseEntity<String> defineEmailAccountToScan(@PathVariable("ruleId") Long ruleId, @RequestBody  Account  emailAccount) {
        try {
            scanRuleService.defineEmailAccountToScan(ruleId, emailAccount);
            return ResponseEntity.ok("Email account to scan defined successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while defining the email account to scan.");
        }
    }
    @PostMapping("/create")
    public ResponseEntity<String> createScanRule(@RequestBody ScanRule scanrule) {
        try {
        	 scanRuleService.createScanRule(scanrule);
            return ResponseEntity.ok("Scan rule created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the scan rule.");
        }
}*/}