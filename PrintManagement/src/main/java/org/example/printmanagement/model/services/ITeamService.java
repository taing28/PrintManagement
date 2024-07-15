package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.entities.Team;

import java.util.List;

public interface ITeamService {
    List<Team> getAllTeams();
    Team getTeam(int teamId);
    Team createTeam(TeamRequest req) throws Exception;
    Team editTeam(TeamRequest req) throws Exception;
    void deleteTeam(int id) throws Exception;
}
