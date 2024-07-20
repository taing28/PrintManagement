package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.SignUpRequest;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.util.List;

public interface IUserService {
    //GET
    List<User> getAllUser();

    User getUserById(int id) throws Exception;

    //POST
    User createUser(SignUpRequest request) throws Exception;

    //PUT
    User updatePassword(int userId, String password);

    //DELETE
    void changeActiveUser(int userId) throws Exception;

    //GENERAL
    User findUserByEmail(String email);

    User changeUserTeam(int userId, int teamId) throws Exception;
}
