package com.example.demo.entitys;

import javax.persistence.Entity;

@Entity

public class ScanRule extends Rule {
     

    private Integer frequency;
  

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public ScanRule() {
        super();
    }

    public ScanRule( Integer frequency) {
      
        this.frequency = frequency;
    }

	
}
