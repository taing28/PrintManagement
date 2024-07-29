package org.example.printmanagement.controllers;

import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class GeneralController {
    private UserService _userService;

    //Response user information after login
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> takeUserFromToken() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
    //Test role
    @GetMapping("/test-pre-auth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Access");
    }
}
