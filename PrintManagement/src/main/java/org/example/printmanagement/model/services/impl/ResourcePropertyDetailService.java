package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ResourcePropertyDetailRequest;
import org.example.printmanagement.model.entities.ResourcePropertyDetail;
import org.example.printmanagement.model.repositories.ResourcePropertyDetailRepo;
import org.example.printmanagement.model.services.IResourcePropertyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourcePropertyDetailService implements IResourcePropertyDetailService {
    @Autowired
    private ResourcePropertyDetailRepo _detailRepo;

    @Override
    public List<ResourcePropertyDetail> list() {
        return _detailRepo.findAll();
    }

    @Override
    public void create(ResourcePropertyDetailRequest req) throws Exception{
        if (_detailRepo.existsByPropertyDetailName(req.getPropertyDetailName())) {
            throw new Exception("Name already existed");
        }
        if (req.getQuantity() < 0) {
            throw new Exception("Quantity must be positive number");
        }
        _detailRepo.save(req.toEntity());
    }

    @Override
    public void updateQuantity(int propertyDetailId, int quantity) throws Exception {
        if (_detailRepo.existsById(propertyDetailId)) {
            throw new Exception("Property Detail Not Found");
        }
        if (quantity < 0) {
            throw new Exception("Quantity must be positive number");
        }
        ResourcePropertyDetail propertyDetail = _detailRepo.findById(propertyDetailId).get();
        propertyDetail.setQuantity(quantity);
        _detailRepo.save(propertyDetail);
    }

    @Override
    public void importProduct(int propertyDetailId, int quantity) throws Exception {
        if (_detailRepo.existsById(propertyDetailId)) {
            throw new Exception("Property Detail Not Found");
        }
        if (quantity < 0) {
            throw new Exception("Quantity must be positive number");
        }
        ResourcePropertyDetail propertyDetail = _detailRepo.findById(propertyDetailId).get();
        propertyDetail.setQuantity(propertyDetail.getQuantity() + quantity);
        _detailRepo.save(propertyDetail);
    }

    @Override
    public void exportProduct(int propertyDetailId, int quantity) throws Exception {
        if (_detailRepo.existsById(propertyDetailId)) {
            throw new Exception("Property Detail Not Found");
        }
        if (quantity < 0) {
            throw new Exception("Quantity must be positive number");
        }
        ResourcePropertyDetail propertyDetail = _detailRepo.findById(propertyDetailId).get();
        if (quantity > propertyDetail.getQuantity()) {
            throw new Exception("Quantity must be less than stock");
        }
        propertyDetail.setQuantity(propertyDetail.getQuantity() - quantity);
        _detailRepo.save(propertyDetail);
    }

    @Override
    public void delete(int id) throws Exception{
        if (_detailRepo.existsById(id)) {
            throw new Exception("Property Detail Not Found");
        }
        _detailRepo.deleteById(id);
    }
}
