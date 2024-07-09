package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.entities.RefreshToken;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.RefreshTokenRepo;
import org.example.printmanagement.model.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService implements ITokenService {
    @Autowired
    private RefreshTokenRepo _tokenRepo;
    @Value("${ra.jwt.expiration}")
    private int JWT_EXPIRATION;

    @Override
    public RefreshToken createToken(int userId, String jwt) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setUserToken(new User(userId));
        token.setToken(jwt);
        token.setExpiryTime(LocalDateTime.now().plusSeconds(JWT_EXPIRATION / 1000));
        _tokenRepo.save(token);
        return token;
    }
}
