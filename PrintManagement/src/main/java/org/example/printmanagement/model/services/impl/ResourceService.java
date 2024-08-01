package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ResourceRequest;
import org.example.printmanagement.model.entities.Resource;
import org.example.printmanagement.model.repositories.ResourceRepo;
import org.example.printmanagement.model.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceService implements IResourceService {
    @Autowired
    private ResourceRepo _resourceRepo;

    @Override
    public List<Resource> getAll() {
        return _resourceRepo.findAll();
    }

    @Override
    public Resource getById(int resourceId) throws Exception {
        if (!_resourceRepo.existsById(resourceId)) {
            throw new Exception("Resource not found");
        }
        return _resourceRepo.findById(resourceId).get();
    }

    @Override
    public void createResource(String image, ResourceRequest req) throws Exception {
        Resource resource = req.toEntity();
        resource.setImage(image);
        if (_resourceRepo.existsByResourceNameEqualsIgnoreCase(resource.getResourceName())) {
            throw new Exception("Resource's name already exist");
        }
        _resourceRepo.save(resource);
    }

    @Override
    public void updateResource(ResourceRequest req) throws Exception {
        Resource resource = req.toEntity();
        Resource oldResource = _resourceRepo.findById(req.getId()).get();
        resource.setImage(oldResource.getImage());
        //Check name
        if (!resource.getResourceName().equals(oldResource.getResourceName()) && _resourceRepo.existsByResourceNameEqualsIgnoreCase(resource.getResourceName())) {
            throw new Exception("Resource's name already exist");
        }
        _resourceRepo.save(resource);
    }

    @Override
    public void deleteResource(int resourceId) throws Exception {
        if (!_resourceRepo.existsById(resourceId)) {
            throw new Exception("Resource not found");
        }
        _resourceRepo.deleteById(resourceId);
    }
}
