package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Boolean isActive;
    private Set<String> setRoles;

    public JwtResponse(String token, String username, Boolean isActive, Set<String> setRoles) {
        this.token = token;
        this.username = username;
        this.isActive = isActive;
        this.setRoles = setRoles;
    }
}
