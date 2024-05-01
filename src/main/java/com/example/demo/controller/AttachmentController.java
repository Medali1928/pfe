package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.Attachment;
import com.example.demo.repository.EmailRepository;
import com.example.demo.service.AttachmentService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/emails/{emailId}/attachments")
public class AttachmentController {
	 @Autowired
	 AttachmentService attachmentService;
	 @Autowired
	 EmailRepository EmailRepo;

	   
	    @DeleteMapping("/{attachmentId}")
	    public ResponseEntity<String> deleteAttachment(@PathVariable("attachmentId") Long attachmentId) {
	        try {
	            attachmentService.deleteAttachment(attachmentId);
	            return ResponseEntity.ok("Attachment deleted successfully.");
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the attachment.");
	        }
	    }
	    @GetMapping("/{attachmentId}")
	    public ResponseEntity<byte[]> getAttachment(@PathVariable Long emailId, @PathVariable Long attachmentId) {
	        Optional<Attachment> attachmentOptional = attachmentService.getAttachmentByEmailIdAndAttachmentId(emailId, attachmentId);
	        if (attachmentOptional.isPresent()) {
	            Attachment attachment = attachmentOptional.get();
	            return ResponseEntity.ok()
	                    .header("Content-Disposition", "attachment; filename=\"" + attachment.getFilename() + "\"")
	                    .body(attachment.getData());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    

}
