package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.ClassificationRule;


@Repository
public interface ClassificationRuleRepository extends JpaRepository<ClassificationRule,Long> {

}
