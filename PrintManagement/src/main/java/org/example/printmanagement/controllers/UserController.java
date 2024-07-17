package org.example.printmanagement.controllers;

import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService _userService;

    //GET METHOD
    /**
     * @method GET
     * @path /users
     * @return List < User> - list users
     */
    @GetMapping()
    private ResponseEntity<?> getAll(){
        return ResponseEntity.ok(_userService.getAllUser());
    }

    /**
     * @method GET
     * @path /users/{id}
     * @param id id of user
     * @return User - user match with id
     */
    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(_userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //POST METHOD

    //PUT METHOD
    /**
     * @method PUT
     * @path /users/change-team
     * @param userId id of user that transfer
     * @param teamId id of team that user transfer to
     * @return User - user that transfer
     */
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/change-team")
    private ResponseEntity<?> editTeamMember(@RequestParam int userId, int teamId) {
        try {
            User user = _userService.changeUserTeam(userId, teamId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
