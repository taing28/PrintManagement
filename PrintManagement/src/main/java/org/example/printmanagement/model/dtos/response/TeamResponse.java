package org.example.printmanagement.model.dtos.response;

import jakarta.persistence.NamedEntityGraph;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

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
        res.setNumberOfMember(team.getNumberOfMember());
        res.setMembers(team.getUserList());
        return res;
    }
}
