package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService _customerService;

    //GET METHOD
    @PreAuthorize("hasRole('ADMIN') or hasRole('LEADER') or hasRole('MANAGER')")
    @GetMapping()
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(_customerService.list());
    }
}
