package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.DeliveryRequest;
import org.example.printmanagement.model.dtos.response.DeliveryResponse;

import java.util.List;

public interface IDeliveryService {
    List<DeliveryResponse> list() throws Exception;

    void create(DeliveryRequest req) throws Exception;

    void update(DeliveryRequest req) throws Exception;

    void delete(int id) throws Exception;
}
