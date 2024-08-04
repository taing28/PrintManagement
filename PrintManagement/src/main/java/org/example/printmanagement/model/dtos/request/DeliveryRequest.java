package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.*;

@Data
@NoArgsConstructor
public class DeliveryRequest {
    private int id;
    private int customerId;
    private int deliverId;
    private String deliveryAddress;
    private int projectId;
    private int shippingMethodId;

    public Delivery toEntity() {
        Delivery delivery = new Delivery();
        if(this.id != 0) {
            delivery.setId(this.id);
        }
        delivery.setCustomerId(this.customerId);
        delivery.setCustomerDelivery(new Customer(this.customerId));
        delivery.setDeliverId(this.deliverId);
        delivery.setDeliveryAddress(this.deliveryAddress);
        delivery.setProjectId(this.projectId);
        delivery.setProjectDelivery(new Project(this.projectId));
        delivery.setShippingMethodId(this.shippingMethodId);
        delivery.setShippingMethod(new ShippingMethod(this.shippingMethodId));
        return delivery;
    }
}
