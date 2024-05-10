package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitys.DomainEntity;
import com.example.demo.service.DomainEntityService;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping("/api/v1/auth")
public class DomainEntityController {
    @Autowired
    private DomainEntityService domainEntityService;

    @PostMapping("/domains")
    public DomainEntity createDomain(@RequestBody DomainEntity domainEntity) {
        return domainEntityService.createDomain(domainEntity);
    }
}
