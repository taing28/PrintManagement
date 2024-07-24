package org.example.printmanagement.controllers;

import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.services.impl.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "*")
public class NotifyController {
    @Autowired
    private NotifyService _notifyService;

    @GetMapping()
    public ResponseEntity<?> list() {
        List<Notification> list = _notifyService.list();
        return ResponseEntity.ok(list.isEmpty() ? list : "There is no notify");
    }
}
