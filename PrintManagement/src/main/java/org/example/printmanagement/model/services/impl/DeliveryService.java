package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.DeliveryRequest;
import org.example.printmanagement.model.dtos.response.DeliveryResponse;
import org.example.printmanagement.model.entities.Delivery;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.DeliveryRepo;
import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeliveryService implements IDeliveryService {
    @Autowired
    private DeliveryRepo _deliveryRepo;
    @Autowired
    private UserRepo _userRepo;

    @Override
    public List<DeliveryResponse> list() throws Exception{
        List<DeliveryResponse> list = new ArrayList<>();
        List<Delivery> deliveries = _deliveryRepo.findAll();
        for (Delivery delivery : deliveries) {
            User deliver = _userRepo.findById(delivery.getDeliverId()).get();
            if(deliver == null) {
                throw new Exception("User not found");
            }
            list.add(DeliveryResponse.toDTO(delivery, deliver));
        }
        return list;
    }

    @Override
    public void create(DeliveryRequest req) throws Exception {

    }

    @Override
    public void update(DeliveryRequest req) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
