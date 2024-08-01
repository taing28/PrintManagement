package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ResourceForPrintRequest;
import org.example.printmanagement.model.entities.ResourceForPrintJob;
import org.example.printmanagement.model.repositories.ResourceForPrintJobRepo;
import org.example.printmanagement.model.services.IResourceForPrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceForPrintJobService implements IResourceForPrintJobService {
    @Autowired
    private ResourceForPrintJobRepo _resourceForPrintJobRepo;

    @Override
    public List<ResourceForPrintJob> list() {
        return _resourceForPrintJobRepo.findAll();
    }

    @Override
    public void create(ResourceForPrintRequest req) throws Exception {
        _resourceForPrintJobRepo.save(req.toResourceForPrintJob());
    }

    @Override
    public void update(ResourceForPrintRequest req) throws Exception {
        //Edit
    }

    @Override
    public void delete(int id) throws Exception {
        if(!_resourceForPrintJobRepo.existsById(id)) {
            throw new Exception("Resource for Print not found");
        }
        _resourceForPrintJobRepo.deleteById(id);
    }
}
