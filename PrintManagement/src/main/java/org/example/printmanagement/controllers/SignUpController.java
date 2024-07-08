package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.services.impl.MailService;
import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/sign-up")
public class SignUpController {
    @Autowired
    private UserService _userService;
    @Autowired
    private MailService _mailService;

    @PostMapping()
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {

    }
}
