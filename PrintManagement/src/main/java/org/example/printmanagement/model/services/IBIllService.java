package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.BillRequest;
import org.example.printmanagement.model.dtos.request.ConfirmResourceRequest;
import org.example.printmanagement.model.entities.Bill;

import java.util.List;

public interface IBIllService {
    List<Bill> list();

    Bill getByProject(int id);

    void create(BillRequest req);

    void updateStatus(BillRequest req);

    void countTotalMoney(int billId, List<ConfirmResourceRequest> requestList);
}
