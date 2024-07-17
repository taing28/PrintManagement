package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.dtos.response.TeamResponse;
import org.example.printmanagement.model.entities.Team;

import java.util.List;

public interface ITeamService {
    List<TeamResponse> getAllTeams();
    TeamResponse getTeam(int teamId) throws Exception;
    Team createTeam(TeamRequest req) throws Exception;
    Team editTeam(TeamRequest req) throws Exception;
    void deleteTeam(int id) throws Exception;
    void updateMembers();
}
