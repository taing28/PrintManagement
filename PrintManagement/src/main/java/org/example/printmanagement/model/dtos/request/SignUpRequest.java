package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String cfPassword;
    private String fullname;
    private LocalDate dateOfBirth;
    private String avatar;
    private String email;
    private String phoneNumber;
    private int teamId;

    public User toEntity() {
        User user = new User();
        user.setUserName(this.username);
        user.setFullName(this.fullname);
        user.setDateOfBirth(this.dateOfBirth);
        user.setAvatar(this.avatar);
        user.setEmail(this.email);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setPhoneNumber(this.phoneNumber);
        user.setTeamId(this.teamId);
        user.setTeam(new Team(this.teamId));
        user.setActive(true);

        return user;
    }
}
