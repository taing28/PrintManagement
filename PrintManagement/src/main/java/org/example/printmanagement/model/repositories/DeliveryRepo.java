package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
}
