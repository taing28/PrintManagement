package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.PrintJobRequest;
import org.example.printmanagement.model.services.impl.PrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/print-job")
public class PrintJobController {
    @Autowired
    private PrintJobService _printJobService;

    //GET METHOD
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(_printJobService.getAll());
    }

    //POST METHOD
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PrintJobRequest req) {
        try {
            _printJobService.createPrintJob(req);
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT METHOD
    @PutMapping()
    public ResponseEntity<?> confirm(@RequestBody PrintJobRequest req) {
        try {
            _printJobService.confirmPrintJob(req);
            return ResponseEntity.ok("Confirmed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD
    @DeleteMapping("/{printId}")
    public ResponseEntity<?> delete(@PathVariable int printId) {
        try {
            _printJobService.deletePrintJob(printId);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
