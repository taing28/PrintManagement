package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.TeamRequest;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.repositories.TeamRepo;
import org.example.printmanagement.model.services.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Transactional(rollbackFor = Exception.class)
@Service
public class TeamService implements ITeamService {
    @Autowired
    private TeamRepo _teamRepo;

    @Override
    public List<Team> getAllTeams() {
        return _teamRepo.findAll();
    }

    @Override
    public Team getTeam(int teamId){
        return _teamRepo.findById(teamId).get();
    }

    @Override
    public Team createTeam(TeamRequest req) throws Exception{
        Team team = req.toEntity();
        team.setCreateTime(LocalDateTime.now());
        team.setNumberOfMember(0);
        if(_teamRepo.existsByNameEqualsIgnoreCase(team.getName())) {
            throw new Exception("Team name already existed");
        }
        return _teamRepo.save(team);
    }

    @Override
    public Team editTeam(TeamRequest req) throws Exception{
        Team team = req.toEntity();
        //Take old team
        Team oldTeam = _teamRepo.findById(team.getId()).get();
        team.setNumberOfMember(oldTeam.getNumberOfMember());
        team.setCreateTime(oldTeam.getCreateTime());
        //Check if new name existed and not equal prev name
        if(_teamRepo.existsByNameEqualsIgnoreCase(team.getName()) && !team.getName().equals(oldTeam.getName())) {
            throw new Exception("Team name already existed");
        }
        return _teamRepo.save(team);
    }

    @Override
    public void deleteTeam(int id) throws Exception{
        if(!_teamRepo.existsById(id)){
            throw new Exception("Team id is not existed");
        }
        _teamRepo.deleteById(id);
    }
}
