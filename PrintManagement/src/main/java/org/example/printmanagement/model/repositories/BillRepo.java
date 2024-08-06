package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer> {
    Bill findBillByProjectId(int projectId);
}
