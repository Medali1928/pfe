package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.ArchivingRule;


@Repository
public interface ArchivingRuleRepository  extends JpaRepository <ArchivingRule,Long>{
    List<ArchivingRule> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<ArchivingRule> findBySenderAndDateBetween(String sender, LocalDate startDate, LocalDate endDate);

    void deleteAllByDateBefore(LocalDate oneYearAgo);

   

}
