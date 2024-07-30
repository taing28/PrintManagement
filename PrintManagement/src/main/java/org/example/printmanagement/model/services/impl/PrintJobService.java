package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.PrintJobRequest;
import org.example.printmanagement.model.entities.PrintJob;
import org.example.printmanagement.model.repositories.PrintJobRepo;
import org.example.printmanagement.model.services.IPrintJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrintJobService implements IPrintJobService {
    @Autowired
    private PrintJobRepo _printJobRepo;

    @Override
    public List<PrintJob> getAll() {
        return _printJobRepo.findAll();
    }

    @Override
    public void createPrintJob(PrintJobRequest req) throws Exception {
        PrintJob printJob = req.toEntity();
        _printJobRepo.save(printJob);
    }

    @Override
    public void confirmPrintJob(PrintJobRequest req) throws Exception {
        PrintJob printJob = req.toEntity();
        if(!_printJobRepo.existsById(req.getId())) {
            throw new Exception("Print Job not found");
        }
        _printJobRepo.save(printJob);
    }

    @Override
    public void deletePrintJob(int printJobId) throws Exception {
        if(!_printJobRepo.existsById(printJobId)) {
            throw new Exception("Print Job not found");
        }
        _printJobRepo.deleteById(printJobId);
    }
}
