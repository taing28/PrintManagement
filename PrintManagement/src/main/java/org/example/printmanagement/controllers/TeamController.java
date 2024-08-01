package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.dtos.response.TeamResponse;
import org.example.printmanagement.model.services.impl.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService _teamService;

    //GET METHOD
    /**
     * @method GET
     * @path /teams
     * @return List < Team> - list team
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping()
    public ResponseEntity<?> getAllTeams() {
        List<TeamResponse> responseList = _teamService.getAllTeams();
        if (responseList.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no Team");
        }
        return ResponseEntity.ok(responseList);
    }

    /**
     * @method GET
     * @path /teams/{id}
     * @param id team id
     * @return Team - team that match id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable int id) {
        try {
            TeamResponse team = _teamService.getTeam(id);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //POST METHOD
    /**
     * @method POST
     * @path /teams
     * @param req
     * @return Team - team that created
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest req) {
        try {
            return ResponseEntity.ok(_teamService.createTeam(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //PUT METHOD
    /**
     * @method PUT
     * @path /teams
     * @param req = team request dto
     * @return Team - team after edit
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<?> editTeam(@RequestBody TeamRequest req) {
        try {
            return ResponseEntity.ok(_teamService.editTeam(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * @method PUT
     * @path /teams/change-manager
     * @param teamId id of team modify
     * @param managerId new manager id
     * @return Team - team that change manager
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change-manager")
    public ResponseEntity<?> editManager(@RequestParam int teamId, int managerId) {
        try {
            return ResponseEntity.ok(_teamService.changeManager(teamId, managerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD
    /**
     * @method DELETE
     * @path /teams/{id}
     * @param id id of deleted team
     * @return String - result message
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable int id) {
        try {
            _teamService.deleteTeam(id);
            return ResponseEntity.ok("Delete successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
