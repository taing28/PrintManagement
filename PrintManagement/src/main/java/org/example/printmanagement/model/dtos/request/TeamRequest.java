package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Team;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TeamRequest {
    private int id;
    private String name;
    private String description;
    private int numberOfMember;
    private int managerId;

    public Team toEntity() {
        Team team = new Team();
        if(this.id != 0) {//Check if id > 0
            team.setId(this.id);
        }
        team.setName(this.name);
        team.setDescription(this.description);
        team.setUpdateTime(LocalDateTime.now());
        team.setManagerId(this.getManagerId());
        return team;
    }

}
