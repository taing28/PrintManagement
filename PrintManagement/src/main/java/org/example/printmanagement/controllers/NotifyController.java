package org.example.printmanagement.controllers;

import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.services.impl.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "*")
public class NotifyController {
    @Autowired
    private NotifyService _notifyService;

    @GetMapping("/{id}")
    public ResponseEntity<?> list(@PathVariable int id) {
        List<Notification> list = _notifyService.listByUserId(id);
        return ResponseEntity.ok(list.isEmpty() ? list : "There is no notify");
    }
}
