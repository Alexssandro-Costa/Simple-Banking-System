package com.project.simple_banking_system.controller;

import com.project.simple_banking_system.model.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simple_banking_system.model.DTOs.ClientDTO;
import com.project.simple_banking_system.model.caseUses.RegisterNewClient;

@RequestMapping("/banco")
@RestController
public class BankingSystemController {

    @Autowired
    private RegisterNewClient registerNewClient;
    
    @PostMapping("/register")
    public void register(@RequestBody ClientDTO client) {
        registerNewClient.execute(client);
    }
    
}
