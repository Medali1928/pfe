package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entitys.ArchivingRule;
import com.example.demo.entitys.Email;
import com.example.demo.repository.ArchivingRuleRepository;
import com.example.demo.repository.EmailRepository;

@EnableScheduling
@Service
public class ArchivingRuleServiceImpl implements ArchivingRuleService {

}
