package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService _deliveryService;

    //GET METHOD
    @GetMapping()
    public ResponseEntity<?> list(){
        try {
            return ResponseEntity.ok(_deliveryService.list());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //POST METHOD
}
