package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
    Boolean existsByDeliverId(int deliverId);

    List<Delivery> findAllByDeliverId(int deliverId);
}
