package com.example.demo.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity

public class ScanRule  {

     
    @Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
    private String frequency;
    @OneToOne
    private Account account;
  

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
