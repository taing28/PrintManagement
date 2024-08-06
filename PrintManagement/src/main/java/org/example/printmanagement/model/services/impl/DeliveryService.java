package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.DeliveryRequest;
import org.example.printmanagement.model.dtos.response.DeliveryResponse;
import org.example.printmanagement.model.entities.Delivery;
import org.example.printmanagement.model.entities.DeliveryStatus;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.DeliveryRepo;
import org.example.printmanagement.model.repositories.UserRepo;
import org.example.printmanagement.model.services.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public List<DeliveryResponse> listByDeliver(int id) throws Exception {
        User deliver = _userRepo.findById(id).get();
        if(deliver == null) {
            throw new Exception("User not found");
        }
        List<DeliveryResponse> list = new ArrayList<>();
        List<Delivery> deliveries = _deliveryRepo.findAllByDeliverId(id);
        for (Delivery delivery : deliveries) {
            list.add(DeliveryResponse.toDTO(delivery, deliver));
        }
        return list;
    }

    @Override
    public void create(DeliveryRequest req) throws Exception {
        Delivery delivery = req.toEntity();
        delivery.setDeliveryStatus(DeliveryStatus.PREPARING);
        switch (req.getShippingMethodId()) {
            case 1:
                delivery.setEstimateDeliveryTime(LocalDateTime.now().plusDays(3));
                break;
            case 2:
                delivery.setEstimateDeliveryTime(LocalDateTime.now().plusDays(10));
                break;
            case 3:
                delivery.setEstimateDeliveryTime(LocalDateTime.now().plusDays(7));
                break;
            default:
                break;
        }
        _deliveryRepo.save(delivery);
    }

    @Override
    public void setStatus(int id) throws Exception {
        Delivery delivery = _deliveryRepo.findById(id).get();
        if (delivery == null) {
            throw new Exception("Delivery not found");
        }
        switch (delivery.getDeliveryStatus()) {
            case PREPARING:
                delivery.setDeliveryStatus(DeliveryStatus.SHIPPING);
                delivery.setActualDeliveryTime(LocalDateTime.now());
                break;
            case SHIPPING:
                delivery.setDeliveryStatus(DeliveryStatus.RECEIVED);
                break;
            case RECEIVED:
                throw new Exception("Already delivered");
            default:
                break;
        }
        _deliveryRepo.save(delivery);
    }

    @Override
    public void delete(int id) throws Exception {

    }
}
