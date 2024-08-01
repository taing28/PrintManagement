package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int shippingMethodId;
    @Column(insertable = false, updatable = false)
    private int customerId;
    private int deliverId;
    @Column(insertable = false, updatable = false)
    private int projectId;
    private String deliveryAddress;
    private LocalDateTime estimateDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "shippingMethodId", foreignKey = @ForeignKey(name = "fk_delivery_shipping"))
    @JsonIgnore
    private ShippingMethod shippingMethod;

    @ManyToOne
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "fk_delivery_customer"))
    @JsonIgnore
    private Customer customerDelivery;

    @ManyToOne
    @JoinColumn(name = "projectId", foreignKey = @ForeignKey(name = "fk_delivery_project"))
    @JsonIgnore
    private Project projectDelivery;

}
