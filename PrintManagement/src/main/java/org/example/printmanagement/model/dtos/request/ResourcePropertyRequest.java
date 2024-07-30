package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Resource;
import org.example.printmanagement.model.entities.ResourceProperty;

@Data
@NoArgsConstructor
public class ResourcePropertyRequest {
    private int id;
    private int quantity;
    private int resourceId;
    private String resourcePropName;

    public ResourceProperty toEntity() {
        ResourceProperty resourceProperty = new ResourceProperty();
        if (this.id != 0) {
            resourceProperty.setId(this.id);
        }
        resourceProperty.setQuantity(this.quantity);
        resourceProperty.setResourcePropertyName(this.resourcePropName);
        resourceProperty.setResourceId(resourceId);
        resourceProperty.setResource(new Resource(resourceId));
        return resourceProperty;
    }
}
