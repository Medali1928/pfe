package com.example.demo.entitys;

import javax.persistence.Entity;

@Entity

public class ScanRule extends Rule {
     

    private String frequency;
  

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public ScanRule() {
        super();
    }

    public ScanRule( String frequency) {
      
        this.frequency = frequency;
    }

	
}
