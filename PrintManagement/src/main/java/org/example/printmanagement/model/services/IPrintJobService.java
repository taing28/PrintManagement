package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.PrintJobRequest;
import org.example.printmanagement.model.entities.PrintJob;

import java.util.List;

public interface IPrintJobService {
    List<PrintJob> getAll();

    void createPrintJob(PrintJobRequest req) throws Exception;

    void confirmPrintJob(PrintJobRequest req) throws Exception;

    void deletePrintJob(int printJobId) throws Exception;
}
