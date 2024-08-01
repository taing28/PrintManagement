package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepo extends JpaRepository<Design, Integer> {
    List<Design> findAllByProjectId(int projectId);
}
