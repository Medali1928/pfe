package com.example.demo.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attachment {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;

    private String filename;
    private String fileType;
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "email_id")
    private Email email;
	public Attachment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Attachment(Long id, String filename, String fileType,byte[] data,  Email email) {
		super();
		this.id = id;
		this.filename = filename;
		this.fileType = fileType;
		this.data = data;
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

}

