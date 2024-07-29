package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Design;
import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.entities.Team;
import org.example.printmanagement.model.entities.User;
import org.springframework.security.core.GrantedAuthority;

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
    private Collection<? extends GrantedAuthority> authorities;

    public static UserResponse toDTO(User user, Collection<? extends GrantedAuthority> authorities) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setUsername(user.getUserName());
        res.setName(user.getFullName());
        res.setDesignList(user.getDesignList());
        res.setNotificationList(user.getNotificationList());
        res.setTeam(user.getTeam());
        res.setAuthorities(authorities);
        return res;
    }
}
