package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String billName;
    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;
    private BigDecimal totalMoney;
    private int projectId;
    @Column(insertable = false, updatable = false)
    private int customerId;
    private String tradingCode;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();
    @Column(insertable = false, updatable = false)
    private int employeeId;

    @ManyToOne
    @JoinColumn(name = "employeeId", foreignKey = @ForeignKey(name = "fk_bill_employee"))
    @JsonIgnore
    private User employeeBill;

    @ManyToOne
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "fk_bill_customer"))
    @JsonIgnore
    private Customer customerBill;
}
