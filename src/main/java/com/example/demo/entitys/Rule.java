package com.example.demo.entitys;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "Rule")
@Inheritance(strategy = InheritanceType.JOINED)

public  class Rule  {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ruleId;
	private String action;
	 @ManyToOne
	  private Account account;
	public Rule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rule(Long ruleId, String action) {
		super();
		this.ruleId = ruleId;
		this.action = action;
		
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
    public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

}
