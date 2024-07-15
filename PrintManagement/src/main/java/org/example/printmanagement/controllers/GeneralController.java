package org.example.printmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GeneralController {
    //Response user information after login
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> takeUserFromToken() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
}
