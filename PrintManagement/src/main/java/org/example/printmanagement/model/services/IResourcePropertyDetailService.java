package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ResourcePropertyDetailRequest;
import org.example.printmanagement.model.entities.ResourcePropertyDetail;

import java.util.List;

public interface IResourcePropertyDetailService {
    List<ResourcePropertyDetail> list();

    List<ResourcePropertyDetail> listByResource(int resourceId) throws Exception;

    void create(ResourcePropertyDetailRequest req) throws Exception;

    void updateQuantity(int propertyDetailId, int quantity) throws Exception;

    void importProduct(int propertyDetailId, int quantity) throws Exception;

    void exportProduct(int propertyDetailId, int quantity) throws Exception;

    void delete(int id) throws Exception;
}
