package org.example.printmanagement.controllers;

import org.example.printmanagement.model.services.impl.MailService;
import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/authenticate")
public class AuthController {
    @Autowired
    private UserService _userService;

    //Login method
}
