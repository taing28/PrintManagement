package org.example.printmanagement.security;

import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo _userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = _userRepo.findUserByUserNameEqualsIgnoreCase(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return CustomUserDetail.mapUserToUserDetail(user);
    }
}
