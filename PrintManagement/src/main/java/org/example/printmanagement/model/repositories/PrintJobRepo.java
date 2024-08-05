package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.PrintJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintJobRepo extends JpaRepository<PrintJob, Integer> {
    PrintJob findPrintJobByDesignId(int designId);
}
