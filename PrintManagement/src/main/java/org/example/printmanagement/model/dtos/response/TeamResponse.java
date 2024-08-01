package org.example.printmanagement.model.dtos.response;

import jakarta.persistence.NamedEntityGraph;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamResponse {
    private int id;
    private String name;
    private String description;
    private int managerId;
    private int numberOfMember;
    private List<User> members;

    public static TeamResponse toDTO(Team team) {
        TeamResponse res = new TeamResponse();
        res.setId(team.getId());
        res.setName(team.getName());
        res.setDescription(team.getDescription());
        res.setManagerId(team.getManagerId());
        res.setMembers(team.getUserList());
        res.setNumberOfMember(res.getMembers().size());
        return res;
    }

    public static List<TeamResponse> toListDTO(List<Team> teamList) {
        List<TeamResponse> resList = new ArrayList<>();
        teamList.forEach(team -> {
            resList.add(TeamResponse.toDTO(team));
        });
        return resList;
    }
}
