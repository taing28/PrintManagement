package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.dtos.response.TeamResponse;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.services.impl.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService _teamService;

    @GetMapping()
    private ResponseEntity<?> getAllTeams() {
        List<TeamResponse> responseList = _teamService.getAllTeams();
        if (responseList.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no Team");
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getTeamById(@PathVariable int id) {
        try {
            TeamResponse team = _teamService.getTeam(id);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    private ResponseEntity<?> createTeam(@RequestBody TeamRequest req) {
        try {
            return ResponseEntity.ok(_teamService.createTeam(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping()
    private ResponseEntity<?> editTeam(@RequestBody TeamRequest req) {
        try {
            return ResponseEntity.ok(_teamService.editTeam(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/change-manager")
    private ResponseEntity<?> editManager(@RequestParam int managerId) {

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteTeam(@PathVariable int id) {
        try {
            _teamService.deleteTeam(id);
            return ResponseEntity.ok("Delete successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
