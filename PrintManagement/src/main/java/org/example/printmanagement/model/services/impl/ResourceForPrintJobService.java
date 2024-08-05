package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ConfirmResourceRequest;
import org.example.printmanagement.model.dtos.request.ResourceForPrintRequest;
import org.example.printmanagement.model.entities.*;
import org.example.printmanagement.model.repositories.BillRepo;
import org.example.printmanagement.model.repositories.PrintJobRepo;
import org.example.printmanagement.model.repositories.ResourceForPrintJobRepo;
import org.example.printmanagement.model.repositories.ResourcePropertyDetailRepo;
import org.example.printmanagement.model.services.IResourceForPrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ResourceForPrintJobService implements IResourceForPrintJobService {
    @Autowired
    private ResourceForPrintJobRepo _resourceForPrintJobRepo;
    @Autowired
    private ResourcePropertyDetailRepo _propertyDetailRepo;
    @Autowired
    private PrintJobRepo _printJobRepo;
    @Autowired
    private BillRepo _billRepo;

    @Override
    public List<ResourceForPrintJob> list() {
        return _resourceForPrintJobRepo.findAll();
    }

    @Override
    public void create(ResourceForPrintRequest req) throws Exception {
        _resourceForPrintJobRepo.save(req.toResourceForPrintJob());
    }

    @Override
    public void confirmResource(int designId, List<ConfirmResourceRequest> resourceRequestList) throws Exception {
        PrintJob printJob = _printJobRepo.findPrintJobByDesignId(designId);
        if (printJob == null) {
            throw new Exception("Print Job not found");
        }
        for (ConfirmResourceRequest resourceRequest : resourceRequestList) {
            //Handle quantity
            ResourcePropertyDetail propertyDetail = _propertyDetailRepo.findById(resourceRequest.getId()).get();
            if (propertyDetail.getQuantity() < resourceRequest.getQuantity()) {
                throw new Exception("Quantity must less than stock");
            }
            propertyDetail.setQuantity(propertyDetail.getQuantity() - resourceRequest.getQuantity());
            _propertyDetailRepo.save(propertyDetail);

            //Create resource for print
            ResourceForPrintJob resource = new ResourceForPrintJob();
            resource.setResourcePropertyDetailId(resourceRequest.getId());
            resource.setResourcePropertyDetailPrint(new ResourcePropertyDetail(resourceRequest.getId()));
            resource.setPrintJobId(printJob.getId());
            resource.setPrintJob(new PrintJob(printJob.getId()));
            _resourceForPrintJobRepo.save(resource);
        }
        printJob.setPrintJobStatus(PrintJobStatus.CONFIRM);
        _printJobRepo.save(printJob);
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
