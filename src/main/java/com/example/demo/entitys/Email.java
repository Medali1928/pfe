package com.example.demo.entitys;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
@Entity
public class Email {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private String sender;
    private List<String> recipients;
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
		// TODO Auto-generated constructor stub
	}
	public Email(Long id, String sender, List<String> recipients, String subject, String body, LocalDate date,
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
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
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
		// TODO Auto-generated method stub
		
	}

}
