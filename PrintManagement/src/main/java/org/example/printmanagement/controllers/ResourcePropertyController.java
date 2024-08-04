package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ResourcePropertyRequest;
import org.example.printmanagement.model.services.impl.ResourcePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/resource-property")
public class ResourcePropertyController {
    @Autowired
    private ResourcePropertyService _propertyService;

    //GET METHOD
    @GetMapping()
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(_propertyService.list());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{resourceProperty}")
    public ResponseEntity<?> listByResource(@PathVariable int resourceProperty) {
        try {
            return ResponseEntity.ok(_propertyService.listByResource(resourceProperty));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //POST METHOD
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ResourcePropertyRequest req) {
        try {
            _propertyService.create(req);
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT METHOD
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody ResourcePropertyRequest req) {
        try {
            _propertyService.update(req);
            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            _propertyService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
