package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.ResourcePropertyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcePropertyDetailRepo extends JpaRepository<ResourcePropertyDetail, Integer> {
    Boolean existsByPropertyDetailName(String name);
}
