package org.example.printmanagement.model.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.printmanagement.model.dtos.request.BillRequest;
import org.example.printmanagement.model.entities.Bill;
import org.example.printmanagement.model.entities.BillStatus;
import org.example.printmanagement.model.repositories.BillRepo;
import org.example.printmanagement.model.services.IBIllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class BillService implements IBIllService {
    @Autowired
    private BillRepo _billRepo;

    @Override
    public List<Bill> list() {
        return _billRepo.findAll();
    }

    @Override
    public Bill getByProject(int id) {
        return _billRepo.findBillByProjectId(id);
    }

    @Override
    public void create(BillRequest req) {
        Bill bill = req.toEntity();
        bill.setBillStatus(BillStatus.WAITING);
        bill.setTotalMoney(BigDecimal.valueOf(0));
        //Generate random code for trading code
        String tradingC = RandomStringUtils.randomAlphanumeric(10);
        byte[] bytesData = tradingC.getBytes();
        String encodedData = Base64.getEncoder().encodeToString(bytesData);
        bill.setTradingCode(encodedData);
        _billRepo.save(bill);
    }

    @Override
    public void updateStatus(BillRequest req) {
        //Take old create Time
    }
}
