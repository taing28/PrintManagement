package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.entities.Customer;
import org.example.printmanagement.model.repositories.CustomerRepo;
import org.example.printmanagement.model.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepo _customerRepo;

    @Override
    public List<Customer> list() {
        return _customerRepo.findAll();
    }
}
