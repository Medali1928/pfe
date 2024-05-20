package com.example.demo.entitys;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


@Entity

public class ArchivedEmail {
    @Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;
    private String sender;
    
	
    private String recipients;
    

    private String subject;
    @Lob
    private String body;
    private LocalDate date;
    private String attachments;
    @ManyToOne
	private Account account;
	public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
   
    public ArchivedEmail(String sender, String recipients, String subject, String body, LocalDate date, String attachments,Account account) {
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.attachments = attachments;
        this.account=account;

       
    }
    public ArchivedEmail(){
        super();

    }

}
