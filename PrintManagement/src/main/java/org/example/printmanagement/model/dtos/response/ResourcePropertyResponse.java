package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.ResourceProperty;
import org.example.printmanagement.model.entities.ResourcePropertyDetail;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ResourcePropertyResponse {
    private int id;
    private int resourceId;
    private String name;
    private List<ResourcePropertyDetail> detailList;

    public static ResourcePropertyResponse toDTO(ResourceProperty property) {
        ResourcePropertyResponse res = new ResourcePropertyResponse();
        res.setId(property.getId());
        res.setResourceId(property.getResourceId());
        res.setName(property.getResourcePropertyName());
        res.setDetailList(property.getResourcePropertyDetailList());
        return res;
    }

    public static List<ResourcePropertyResponse> toDTO(List<ResourceProperty> propertyList) {
        List<ResourcePropertyResponse> list = new ArrayList<>();
        for (ResourceProperty property : propertyList) {
            list.add(ResourcePropertyResponse.toDTO(property));
        }
        return list;
    }
}
