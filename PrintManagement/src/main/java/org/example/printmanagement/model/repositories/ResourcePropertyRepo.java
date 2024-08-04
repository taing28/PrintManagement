package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.ResourceProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcePropertyRepo extends JpaRepository<ResourceProperty, Integer> {
    List<ResourceProperty> findAllByResourceId(int resourceId);
}
