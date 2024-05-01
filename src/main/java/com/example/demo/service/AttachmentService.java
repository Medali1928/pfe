package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entitys.Attachment;



public interface AttachmentService {
	 public Optional<Attachment> getAttachmentByEmailIdAndAttachmentId(Long emailId, Long attachmentId);
	 public void deleteAttachment(Long Id);

}
