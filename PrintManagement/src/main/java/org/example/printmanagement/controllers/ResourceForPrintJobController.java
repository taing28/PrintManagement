package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ConfirmResourceRequest;
import org.example.printmanagement.model.dtos.request.ResourceForPrintConfirmRequest;
import org.example.printmanagement.model.services.impl.BillService;
import org.example.printmanagement.model.services.impl.ResourceForPrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@CrossOrigin("*")
@RequestMapping("/resource-for-print")
public class ResourceForPrintJobController {
    @Autowired
    private ResourceForPrintJobService _forPrintJobService;
    @Autowired
    private BillService _billService;

    @PostMapping()
    public ResponseEntity<?> confirmResource(@RequestBody ResourceForPrintConfirmRequest req) {
        try {
            _forPrintJobService.confirmResource(req.getDesignId(), req.getRequestList());
            _billService.countTotalMoney(req.getBillId(), req.getRequestList());
            return ResponseEntity.ok("Confirmed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
