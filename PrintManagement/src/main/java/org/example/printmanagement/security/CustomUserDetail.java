package org.example.printmanagement.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.printmanagement.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    //Tu thong tin user chuyen sang thong tin CustomUserDetails
    public static CustomUserDetail mapUserToUserDetail(User user) {
        //Lay quyen tu doi tuong user
        Set<GrantedAuthority> setAuthorities = user.getPermissionList().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getRolePermiss().getRoleName()))
                .collect(Collectors.toSet());
        //Tra ve doi tuong CustomUserDetails
        return new CustomUserDetail(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                setAuthorities
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
