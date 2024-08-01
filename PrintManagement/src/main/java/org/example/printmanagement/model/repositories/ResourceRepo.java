package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Integer> {
    Boolean existsByResourceNameEqualsIgnoreCase(String name);
}
