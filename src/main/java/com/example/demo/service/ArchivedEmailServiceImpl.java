package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entitys.ArchivedEmail;

import com.example.demo.entitys.Email;
import com.example.demo.repository.ArchivedEmailRepository;

import com.example.demo.repository.EmailRepository;
@Service
public class ArchivedEmailServiceImpl implements ArchivedEmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ArchivedEmailRepository archivedEmailRepository;

    @Override
    @Scheduled(cron = "@monthly")
    public void archiverEmailsSelonRegles() {
        List<Email> emails = emailRepository.findAll();
        for (Email email : emails) {
            Long accountId = email.getAccount().getId();
            LocalDate dateLimite = LocalDate.now().minus(1, ChronoUnit.YEARS);
            List<Email> emailsAarchiver = emailRepository.findByDateBeforeAndAccountId(dateLimite, accountId);
            for (Email emailAarchiver : emailsAarchiver) {
                ArchivedEmail ar = new ArchivedEmail(
                    emailAarchiver.getSender(),
                    emailAarchiver.getRecipients(),
                    emailAarchiver.getSubject(),
                    emailAarchiver.getBody(),
                    emailAarchiver.getDate(),
                    emailAarchiver.getAttachments(),
                    emailAarchiver.getAccount()
                );
                archivedEmailRepository.save(ar);
                emailRepository.delete(emailAarchiver);
            }
        }
    }

    @Override
    public List<ArchivedEmail> getAllArchivedEmails(Long accountId) {
        return archivedEmailRepository.findByAccountId(accountId);
    }

    @Override
    public List<ArchivedEmail> getArchivedEmailsByDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        return archivedEmailRepository.findByDateBetweenAndAccountId(startDate, endDate, accountId);
    }

    @Override
    public List<ArchivedEmail> getArchivedEmailsBySenderAndDateRange(Long accountId, String sender, LocalDate startDate, LocalDate endDate) {
        return archivedEmailRepository.findBySenderAndDateBetweenAndAccountId(sender, startDate, endDate, accountId);
    }

    @Override
    public ArchivedEmail getArchivedEmailById(Long accountId, Long id) {
        return archivedEmailRepository.findByIdAndAccountId(id, accountId).orElse(null);
    }

    @Override
    public void deleteArchivedEmail(Long accountId, ArchivedEmail archivedEmail) {
        if (archivedEmail.getAccount().getId().equals(accountId)) {
            archivedEmailRepository.delete(archivedEmail);
        }
    }

    @Override
    @Transactional
    public void deleteArchivedEmailsOlderThanOneYear(Long accountId) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        archivedEmailRepository.deleteAllByDateBeforeAndAccountId(oneYearAgo, accountId);
    }

}
