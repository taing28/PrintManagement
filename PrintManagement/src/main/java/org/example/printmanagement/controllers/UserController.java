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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(_userService.getAllUser());
    }

    /**
     * @method GET
     * @path /users/{id}
     * @param id id of user
     * @return User - user match with id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
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
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-team")
    public ResponseEntity<?> editTeamMember(@RequestParam int userId, int teamId) {
        try {
            User user = _userService.changeUserTeam(userId, teamId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * /@method PUT
     * @path /users/{id}
     * @param id id of user
     * @return change status of user
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeActiveUser(@PathVariable int id) {
        try {
            _userService.changeActiveUser(id);
            return ResponseEntity.ok("Change successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD

}
