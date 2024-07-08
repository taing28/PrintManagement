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
public class ImportCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal totalMoney;
    @Column(insertable = false, updatable = false)
    private int resourcePropertyDetailId;
    @Column(insertable = false, updatable = false)
    private int employeeId;
    private String tradingCode;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "resourcePropertyDetailId", foreignKey = @ForeignKey(name = "fk_coupon_resrcPropertyDetail"))
    @JsonIgnore
    private ResourcePropertyDetail resourcePropertyDetailCoupon;

    @ManyToOne
    @JoinColumn(name = "employeeId", foreignKey = @ForeignKey(name = "fk_coupon_employee"))
    @JsonIgnore
    private User employeeCoupon;
}
