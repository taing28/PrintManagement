package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {
    Boolean existsByProjectNameEqualsIgnoreCase(String name);
}
