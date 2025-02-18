package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ResourcePropertyRequest;
import org.example.printmanagement.model.dtos.response.ResourcePropertyResponse;
import org.example.printmanagement.model.entities.ResourceProperty;

import java.util.List;

public interface IResourcePropertyService {
    List<ResourceProperty> list() throws Exception;

    List<ResourcePropertyResponse> listByResource(int resourceId) throws Exception;

    void create(ResourcePropertyRequest req) throws Exception;

    void update(ResourcePropertyRequest req) throws Exception;

    void delete(int id) throws Exception;
}
