package org.example.printmanagement.model.services;

import org.example.printmanagement.model.entities.RefreshToken;

public interface ITokenService {
    RefreshToken createToken(int userId, String jwt);
}
