package com.example.demo.entitys;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
@Entity
public class DomainEntity {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	 @ManyToMany(mappedBy = "domainEntities")
	    private Set<Email> emails = new HashSet<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Email> getEmails() {
		return emails;
	}
	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}
	public DomainEntity(Long id, Set<Email> emails) {
		super();
		this.id = id;
		this.emails = emails;
	}
	public DomainEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}