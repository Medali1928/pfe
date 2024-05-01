package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Rule;


@Repository
public interface RuleRepository extends JpaRepository<Rule,Long> {

}
