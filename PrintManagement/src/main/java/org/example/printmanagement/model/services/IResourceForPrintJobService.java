package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ResourceForPrintRequest;
import org.example.printmanagement.model.entities.ResourceForPrintJob;

import java.util.List;

public interface IResourceForPrintJobService {
    List<ResourceForPrintJob> list();

    void create(ResourceForPrintRequest req) throws Exception;

    void update(ResourceForPrintRequest req) throws Exception;

    void delete(int id) throws Exception;
}
