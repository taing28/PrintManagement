package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.ResourceProperty;
import org.example.printmanagement.model.entities.ResourcePropertyDetail;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ResourcePropertyDetailRequest {
    private int id;
    private String imageUrl;
    private BigDecimal price;
    private String propertyDetailName;
    private int propertyId;
    private int quantity;

    public ResourcePropertyDetailRequest(int id, String imageUrl, BigDecimal price, String propertyDetailName, int propertyId, int quantity) {
        if (id != 0) {
            this.id = id;
        }
        this.imageUrl = imageUrl;
        this.price = price;
        this.propertyDetailName = propertyDetailName;
        this.propertyId = propertyId;
        this.quantity = quantity;
    }

    public ResourcePropertyDetail toEntity() {
        ResourcePropertyDetail propertyDetail = new ResourcePropertyDetail();
        propertyDetail.setId(this.id);
        propertyDetail.setImage(this.imageUrl);
        propertyDetail.setPrice(this.price);
        propertyDetail.setPropertyDetailName(this.propertyDetailName);
        propertyDetail.setPropertyId(this.propertyId);
        propertyDetail.setResourceProperty(new ResourceProperty(propertyId));
        propertyDetail.setQuantity(this.quantity);
        return propertyDetail;
    }
}
