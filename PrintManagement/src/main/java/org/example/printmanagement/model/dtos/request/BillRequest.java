package org.example.printmanagement.model.dtos.request;

import org.example.printmanagement.model.entities.Bill;
import org.example.printmanagement.model.entities.BillStatus;
import org.example.printmanagement.model.entities.Customer;
import org.example.printmanagement.model.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillRequest {
    private int id;
    private String billName;
    private String billStatus;
    private BigDecimal totalMoney;
    private int projectId;
    private int customerId;
    private LocalDateTime updateTime;
    private int employeeId;

    public Bill toEntity() {
        Bill bill = new Bill();
        if (this.id != 0) {
            bill.setId(this.id);
            switch (this.billStatus) {
                case "waiting":
                    bill.setBillStatus(BillStatus.WAITING);
                    break;
                case "confirm":
                    bill.setBillStatus(BillStatus.CONFIRMED);
                    break;
                case "completed":
                    bill.setBillStatus(BillStatus.COMPLETED);
                    break;
                case "cancel":
                    bill.setBillStatus(BillStatus.CANCELLED);
                default:
                    break;
            }
        }
        bill.setBillName(this.billName);
        bill.setProjectId(this.projectId);
        bill.setCustomerId(this.customerId);
        bill.setCustomerBill(new Customer(this.customerId));
        bill.setCreateTime(LocalDateTime.now());
        bill.setUpdateTime(LocalDateTime.now());
        bill.setEmployeeId(this.employeeId);
        bill.setEmployeeBill(new User(this.employeeId));
        return bill;
    }
}
