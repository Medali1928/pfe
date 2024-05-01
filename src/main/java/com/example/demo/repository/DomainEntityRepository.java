package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.DomainEntity;

@Repository

public interface DomainEntityRepository extends JpaRepository<DomainEntity,Long>{

}
