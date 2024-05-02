package com.example.demo.entitys;

import javax.persistence.Entity;

@Entity

public class ClassificationRule extends Rule {
  

    private String category;
   

    public ClassificationRule() {
        super();
    }

    public ClassificationRule( String category) {
       
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	
}

