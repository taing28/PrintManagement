package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.Permission;
import org.example.printmanagement.model.entities.Role;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.PermissionRepo;
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
    private PermissionRepo _permissionRepo;
    @Autowired
    private PasswordEncoder _encoder;

    @Override
    public User createUser(SignUpRequest request) throws Exception {
        if (!request.getPassword().equals(request.getCfPassword())) {
            throw new Exception("Password not matched");
        }
        if (_userRepo.existsByUserNameEqualsIgnoreCase(request.getUsername())) {
            throw new Exception("Username already existed");
        }
        User user = request.toEntity();
        user.setPassword(_encoder.encode(request.getPassword()));
        //Save user for taking id
        user = _userRepo.save(user);
        _permissionRepo.save(new Permission(user.getId(), 4, new User(user.getId()), new Role(4)));
        return user;
    }

    @Override
    public User updatePassword(int userId, String password) {
        User user = _userRepo.findById(userId).get();
        user.setPassword(_encoder.encode(password));
        return _userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return _userRepo.findUserByEmail(email);
    }
}
