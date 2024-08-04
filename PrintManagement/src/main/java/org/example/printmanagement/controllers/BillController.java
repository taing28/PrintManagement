package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/bills")
public class BillController {
    @Autowired
    private BillService _billService;

    //GET METHOD
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getByProject(@PathVariable int projectId) {
        return ResponseEntity.ok(_billService.getByProject(projectId));
    }
}
