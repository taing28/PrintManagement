package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Design;
import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String name;
    private String username;
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
        res.setDesignList(user.getDesignList());
        res.setNotificationList(user.getNotificationList());
        res.setTeam(user.getTeam());
        res.setAuthorities(authorities);
        res.setIsActive(user.isActive());
        return res;
    }
}
