package com.example.demo.entitys;




import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.persistence.InheritanceType;
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
