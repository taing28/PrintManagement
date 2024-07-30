package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Resource;
import org.example.printmanagement.model.entities.ResourceStatus;
import org.example.printmanagement.model.entities.ResourceType;

@Data
@NoArgsConstructor
public class ResourceRequest {
    private int id;
    private String name;
    private String resourceType;
    private int availableQuantity;
    private String resourceStatus;

    public ResourceRequest(String name, String resourceType, int availableQuantity, String resourceStatus) {
        this.name = name;
        this.resourceType = resourceType;
        this.availableQuantity = availableQuantity;
        this.resourceStatus = resourceStatus;
    }

    public ResourceRequest(int id, String name, String resourceType, int availableQuantity, String resourceStatus) {
        this.id = id;
        this.name = name;
        this.resourceType = resourceType;
        this.availableQuantity = availableQuantity;
        this.resourceStatus = resourceStatus;
    }

    public Resource toEntity(){
        Resource resource = new Resource();
        if(this.id != 0) {
            resource.setId(this.id);
        }
        resource.setResourceName(this.name);
        resource.setAvailableQuantity(this.availableQuantity);
        //set resourceType
        switch (this.resourceType) {
            case "renewable":
                resource.setResourceType(ResourceType.RENEWABLE);
                break;
            case "nonrenewable":
                resource.setResourceType(ResourceType.NONRENEWABLE);
                break;
            default:
                break;
        }
        //set resourceStatus
        switch (this.resourceStatus) {
            case "available":
                resource.setResourceStatus(ResourceStatus.AVAILABLE);
                break;
            case "not-available":
                resource.setResourceStatus(ResourceStatus.NOT_AVAILABLE);
                break;
            default:
                break;
        }
        return resource;
    }
}
