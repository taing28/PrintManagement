package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.ResourceForPrintJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceForPrintJobRepo extends JpaRepository<ResourceForPrintJob, Integer> {
}
