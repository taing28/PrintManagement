package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.services.impl.MailService;
import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/register")
public class SignUpController {
    @Autowired
    private UserService _userService;
    @Autowired
    private MailService _mailService;

    @GetMapping()
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Access");
    }

    @PostMapping()
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            _userService.createUser(request);
            _mailService.sendEmailWithHTML(request.getEmail(), "Welcome to Print Manager Community", "<h1>Hello there,</h1>\"<h4>Have fun and give it your all ^^!</h4>");
            return ResponseEntity.ok("Sign up successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
