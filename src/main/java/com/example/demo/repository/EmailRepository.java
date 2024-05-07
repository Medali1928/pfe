package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Email;

@Repository
public interface EmailRepository extends JpaRepository <Email,Long>{
	List<Email> findByDateBeforeAndArchivedFalse(LocalDate dateLimite);
	//Email save(Email email);

   
	
	
	

}
