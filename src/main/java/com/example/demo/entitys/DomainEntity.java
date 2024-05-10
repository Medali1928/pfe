package com.example.demo.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class DomainEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String domainName;

    @ManyToMany(mappedBy = "domainEntities")
    private Set<Email> emails = new HashSet<>();
    
}