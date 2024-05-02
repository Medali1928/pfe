package com.example.demo.entitys;

import javax.persistence.Entity;

@Entity

public class ArchivingRule extends Rule {
	
   


    private Integer retentionPeriod;
    
 

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

	
}
