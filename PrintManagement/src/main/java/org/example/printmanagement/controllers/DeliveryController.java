package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{deliverId}")
    public ResponseEntity<?> listByDeliver(@PathVariable int deliverId){
        try {
            return ResponseEntity.ok(_deliveryService.listByDeliver(deliverId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //PUT METHOD
    @PutMapping("/{deliveryId}")
    public ResponseEntity<?> setStatus(@PathVariable int deliveryId) {
        try {
            _deliveryService.setStatus(deliveryId);
            return ResponseEntity.ok("Set successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
