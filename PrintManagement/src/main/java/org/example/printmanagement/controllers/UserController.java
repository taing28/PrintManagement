package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService _userService;

    @PutMapping()
    private ResponseEntity<?> updateUser() {

    }
}
