package com.example.demo.entitys;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Setter;
import lombok.Getter;



@Setter
@Getter


@Entity

public class ArchivingRule extends Rule {
	
   
  
     
    private String sender;
    
	
    private String recipients;
    

    private String subject;
    @Lob
    private String body;
    private LocalDate date;
    private String attachments;
    public ArchivingRule (String sender, String recipients, String subject, String body, LocalDate date,String attachments) {
super();

this.sender = sender;
this.recipients = recipients;
this.subject = subject;
this.body = body;
this.date = date;
this.attachments = attachments;
}
   /* private Integer retentionPeriod;
    
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
 

    public Integer getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(Integer retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public ArchivingRule() {
        super();
    }

    public ArchivingRule( Integer retentionPeriod) {
        
        this.retentionPeriod = retentionPeriod;
    }

	*/
}
