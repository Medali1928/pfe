package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.ArchivingRule;


@Repository
public interface ArchivingRuleRepository  extends JpaRepository <ArchivingRule,Long>{

}
