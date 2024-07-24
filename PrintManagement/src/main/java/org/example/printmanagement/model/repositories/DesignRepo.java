package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepo extends JpaRepository<Design, Integer> {
}
