package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.util.List;

public interface IUserService {
    User createUser(SignUpRequest request) throws Exception;

    User updatePassword(int userId, String password);

    User findUserByEmail(String email);

    User changeUserTeam(int userId, int teamId) throws Exception;
}
