package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.ResourcePropertyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/property-detail")
public class ResourcePropertyDetailController {
    @Autowired
    private ResourcePropertyDetailService _propertyDetailService;

    //GET METHOD
    @GetMapping()
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(_propertyDetailService.list());
    }
}
