package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.User;

public interface IUserService {
    User createUser(SignUpRequest request) throws Exception;

    User updatePassword(int userId, String password);

    User findUserByEmail(String email);
}
