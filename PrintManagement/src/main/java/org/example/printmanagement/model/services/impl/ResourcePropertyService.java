package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ResourcePropertyRequest;
import org.example.printmanagement.model.dtos.response.ResourcePropertyResponse;
import org.example.printmanagement.model.entities.ResourceProperty;
import org.example.printmanagement.model.repositories.ResourcePropertyRepo;
import org.example.printmanagement.model.services.IResourcePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourcePropertyService implements IResourcePropertyService {
    @Autowired
    private ResourcePropertyRepo _resourcePropertyRepo;

    @Override
    public List<ResourceProperty> list() throws Exception {
        List<ResourceProperty> list = _resourcePropertyRepo.findAll();
        if (list.isEmpty()) {
            throw new Exception("There is no resource prop");
        }
        return list;
    }

    @Override
    public List<ResourcePropertyResponse> listByResource(int resourceId) throws Exception {
        List<ResourceProperty> resourceProperties = _resourcePropertyRepo.findAllByResourceId(resourceId);
        return ResourcePropertyResponse.toDTO(resourceProperties);
    }

    @Override
    public void create(ResourcePropertyRequest req) throws Exception {
        ResourceProperty resourceProperty = req.toEntity();
        //Check duplicate if needed
        _resourcePropertyRepo.save(resourceProperty);
    }

    @Override
    public void update(ResourcePropertyRequest req) throws Exception {
        ResourceProperty resourceProperty = req.toEntity();
        if (!_resourcePropertyRepo.existsById(req.getId())) {
            throw new Exception("Property not found");
        }
        _resourcePropertyRepo.save(resourceProperty);
    }

    @Override
    public void delete(int id) throws Exception {
        if (!_resourcePropertyRepo.existsById(id)) {
            throw new Exception("Property not found");
        }
        _resourcePropertyRepo.deleteById(id);
    }
}
