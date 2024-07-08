package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepo _userRepo;
    @Autowired
    private PasswordEncoder _encoder;

    @Override
    public User createUser(SignUpRequest request) throws Exception{
        if (!request.getPassword().equals(request.getCfPassword())) {
            throw new Exception("Password not matched");
        }
        if(_userRepo.existsByUserNameEqualsIgnoreCase(request.getUsername())) {
            throw new Exception("Username already existed");
        }
        User user = request.toEntity();
        user.setPassword(_encoder.encode(request.getPassword()));
        return _userRepo.save(user);
    }
}
