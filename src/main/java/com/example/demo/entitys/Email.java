package com.example.demo.entitys;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Email {
    @Id
    private Long id;

    private String sender;
    
    @ElementCollection
    private Set<String> recipients; // Changed to Set<String> for storing email addresses

    private String subject;
    private String body;
    private LocalDate date;
    private String attachments;
	private boolean archived;
    @OneToMany(mappedBy = "email")
    private List<Attachment> attachments1;

    @ManyToMany
    @JoinTable(name = "email_domain_entity",
               joinColumns = @JoinColumn(name = "email_id"),
               inverseJoinColumns = @JoinColumn(name = "domain_entity_id"))
    private Set<DomainEntity> domainEntities = new HashSet<>();

    // Constructors, getters, setters

    public Set<DomainEntity> getDomainEntities() {
		return domainEntities;
	}
	public void setDomainEntities(Set<DomainEntity> domainEntities) {
		this.domainEntities = domainEntities;
	}
	public void setAttachments(List<Attachment> attachments1) {
		this.attachments1 = attachments1;
	}
    public Email() {
		super();
		
	}
	public Email(Long id, String sender, Set<String> recipients, String subject, String body, LocalDate date,
			String attachments) {
		super();
		this.id = id;
		this.sender = sender;
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.date = date;
		this.attachments = attachments;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Set<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(Set<String> recipients) {
		this.recipients = recipients;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	public void setArchived(boolean b) {
		
		
	}

}
