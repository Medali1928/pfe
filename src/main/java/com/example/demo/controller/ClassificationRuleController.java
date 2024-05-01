package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.ClassificationRule;
import com.example.demo.service.ClassificationRuleService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/classification-rules")
public class ClassificationRuleController {
	 @Autowired
	    ClassificationRuleService classificationRuleService;

	    @PostMapping("/create")
	    public ResponseEntity<String> createClassificationRule(@RequestBody ClassificationRule classificationRule) {
	        try {
	            classificationRuleService.createClassificationRule(classificationRule);
	            return ResponseEntity.ok("Classification rule created successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the classification rule.");
	        }
	    }
	    @GetMapping("/all")
	    public ResponseEntity<List<ClassificationRule>> getAllClassificationRules() {
	        List<ClassificationRule> classificationRules = classificationRuleService.getAllClassificationRules();
	        return ResponseEntity.ok(classificationRules);
	    }
	    @DeleteMapping("/{ruleId}")
	    public ResponseEntity<String> deleteClassificationRule(@PathVariable("ruleId") Long ruleId) {
	        try {
	            classificationRuleService.deleteClassificationRule(ruleId);
	            return ResponseEntity.ok("Classification rule deleted successfully.");
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the classification rule.");
	        }
	    }
	    @PutMapping("/{ruleId}")
	    public ResponseEntity<String> updateClassificationRule(@PathVariable("ruleId") Long ruleId, @RequestBody ClassificationRule updatedRule) {
	        try {
	            classificationRuleService.updateClassificationRule(ruleId, updatedRule);
	            return ResponseEntity.ok("Classification rule updated successfully.");
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the classification rule.");
	        }
	    }

}

