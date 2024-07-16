package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByUserNameEqualsIgnoreCase(String username);

    Boolean existsByUserNameEqualsIgnoreCase(String username);

    User findUserByEmail(String email);

    List<User> findAllByTeamId(int teamId);
}
