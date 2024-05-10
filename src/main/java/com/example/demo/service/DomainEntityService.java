package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entitys.DomainEntity;
import com.example.demo.repository.DomainEntityRepository;
@Service
public class DomainEntityService {
    @Autowired
    private DomainEntityRepository domainEntityRepository;
    public DomainEntity createDomain(DomainEntity domainEntity) {
        // Vous pouvez utiliser directement le domaineEntity reçu du contrôleur
        return domainEntityRepository.save(domainEntity);
    }
    

}
