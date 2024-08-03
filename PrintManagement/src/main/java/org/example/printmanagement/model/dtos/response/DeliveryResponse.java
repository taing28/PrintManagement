package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DeliveryResponse {
    private int id;
    private LocalDateTime actualDeliveryTime;
    private LocalDateTime estimateDeliveryTime;
    private Customer customer;
    private User deliver;
    private String deliveryAddress;
    private String deliveryStatus;
    private Project project;
    private ShippingMethod method;

    public static DeliveryResponse toDTO(Delivery delivery, User deliver) {
        DeliveryResponse res = new DeliveryResponse();
        res.setId(delivery.getId());
        res.setActualDeliveryTime(delivery.getActualDeliveryTime());
        res.setEstimateDeliveryTime(delivery.getEstimateDeliveryTime());
        res.setCustomer(delivery.getCustomerDelivery());
        res.setDeliver(deliver);
        res.setDeliveryAddress(delivery.getDeliveryAddress());
        res.setDeliveryStatus(delivery.getDeliveryStatus().toString());
        res.setProject(delivery.getProjectDelivery());
        res.setMethod(delivery.getShippingMethod());
        return res;
    }
}
