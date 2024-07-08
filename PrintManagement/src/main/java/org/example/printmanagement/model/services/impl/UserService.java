package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepo _userRepo;
}
