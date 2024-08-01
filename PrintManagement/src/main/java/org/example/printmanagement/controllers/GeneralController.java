package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.response.UserResponse;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.services.impl.UserService;
import org.example.printmanagement.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class GeneralController {
    @Autowired
    private UserService _userService;

    //Response user information after login
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> takeUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        int userId = userDetails.getId();
        try {
            User user = _userService.getUserById(userId);
            List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return ResponseEntity.ok(UserResponse.toDTO(user, authorities));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
