package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team, Integer> {
    Boolean existsByNameEqualsIgnoreCase(String name);

    Team findTeamByNameEquals(String name);
}
