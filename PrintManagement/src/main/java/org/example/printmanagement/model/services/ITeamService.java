package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.dtos.response.TeamResponse;
import org.example.printmanagement.model.entities.Team;

import java.util.List;

public interface ITeamService {
    //GET
    List<TeamResponse> getAllTeams();
    TeamResponse getTeam(int teamId) throws Exception;
    //POST
    Team createTeam(TeamRequest req) throws Exception;
    //PUT
    Team editTeam(TeamRequest req) throws Exception;
    Team changeManager(int teamId, int managerId) throws Exception;
    //DELETE
    void deleteTeam(int id) throws Exception;
    //General
    void updateMembers();
}
