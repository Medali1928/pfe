package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.Attachment;
import com.example.demo.entitys.Email;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.EmailRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class AttachementServiceImpl implements AttachmentService {
	@Autowired
	AttachmentRepository attachmentRepo;
	 @Autowired
	 EmailRepository emailRepo;

	@Override
	public Optional<Attachment> getAttachmentByEmailIdAndAttachmentId(Long emailId, Long attachmentId) {
        Optional<Email> emailOptional = emailRepo.findById(emailId);
        if (emailOptional.isPresent()) {
            Email email = emailOptional.get();
            return email.getAttachments1().stream()
                    .filter(attachment -> attachment.getId().equals(attachmentId))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }
	

	@Override
	public void deleteAttachment(Long Id) {
		Attachment attachment = attachmentRepo.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Attachment not found with id: " + Id));

        // Supprimer l'attachement de la base de données
		attachmentRepo.delete(attachment);
		
	}

}
