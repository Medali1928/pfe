package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entitys.ArchivingRule;

public interface ArchivingRuleService {
	public void archiverEmailsSelonRegles();
	public List<ArchivingRule> getAllArchivedEmails();
	public List<ArchivingRule> getArchivedEmailsByDateRange(LocalDate startDate, LocalDate endDate);
	public List<ArchivingRule> getArchivedEmailsBySenderAndDateRange(String sender, LocalDate start, LocalDate end);
	public void deleteArchivedEmail(ArchivingRule archivingRule);
	public ArchivingRule getArchivedEmailById(Long id);
	public void deleteArchivedEmailsOlderThanOneYear();
	


}
