package org.example.printmanagement.controllers;

import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.services.impl.TeamService;
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
    @Autowired
    private TeamService _teamService;

    //Admin, Manager task
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
