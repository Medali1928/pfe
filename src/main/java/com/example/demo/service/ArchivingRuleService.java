package com.example.demo.service;

import com.example.demo.entitys.ArchivingRule;

public interface ArchivingRuleService {
    
    public ArchivingRule addArchivingRule(ArchivingRule archivingRule);
    public ArchivingRule updateArchivingRule(Long id, ArchivingRule archivingRuleDetails) ;
}
