package com.example.demo.repository;

import java.time.LocalDate;

import java.util.List;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Email;

@Repository
public interface EmailRepository extends JpaRepository <Email,Long>{
	
	Email findBySubjectAndDate(String subject, LocalDate date);
	List<Email> findBySenderContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndDateBetween(
		String sender, String subject, LocalDate startDate, LocalDate endDate);
	Email findBySender(String sender);
	//List<Email> findByDateBeforeAndArchivedFalse(LocalDate dateLimite);
	//@Query("UPDATE Email e SET e.archived = true WHERE e.date < ?1")
    //void archiveEmailsBeforeDate(LocalDate date);
	//List<Email> findByArchivedTrue();
	List<Email> findByDateBefore(LocalDate dateLimite);
}

   
	
	
	


