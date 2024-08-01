package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Design;
import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private List<Design> designList;
    private List<Notification> notificationList;
    private Team team;
    private Boolean isActive;
    private List<String> authorities;

    public static UserResponse toDTO(User user, List<String> authorities) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setUsername(user.getUserName());
        res.setName(user.getFullName());
        res.setEmail(user.getEmail());
        res.setPhoneNumber(user.getPhoneNumber());
        res.setDateOfBirth(user.getDateOfBirth());
        res.setDesignList(user.getDesignList());
        res.setNotificationList(user.getNotificationList());
        res.setTeam(user.getTeam());
        res.setAuthorities(authorities);
        res.setIsActive(user.isActive());
        return res;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getFullName();
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dateOfBirth = user.getDateOfBirth();
        this.team = user.getTeam();
        this.isActive = user.isActive();
    }

    public static List<UserResponse> toListDTO(List<User> userList) {
        List<UserResponse> list = new ArrayList<>();
        for(User user : userList) {
            list.add(new UserResponse(user));
        }
        return list;
    }
}
