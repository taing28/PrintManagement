package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ResourceRequest;
import org.example.printmanagement.model.entities.Resource;

import java.util.List;

public interface IResourceService {
    List<Resource> getAll();

    Resource getById(int resourceId) throws Exception;

    void createResource(String image, ResourceRequest req) throws Exception;

    void updateResource(ResourceRequest req) throws Exception;

    void deleteResource(int resourceId) throws Exception;
}
