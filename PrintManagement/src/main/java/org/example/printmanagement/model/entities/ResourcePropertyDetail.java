package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ResourcePropertyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int propertyId;
    private String propertyDetailName;
    private String image;
    private BigDecimal price;
    private int quantity;

    public ResourcePropertyDetail(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "resourcePropertyDetailCoupon", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ImportCoupon> importCouponList;

    @OneToMany(mappedBy = "resourcePropertyDetailPrint", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ResourceForPrintJob> resourceForPrintJobList;

    @ManyToOne
    @JoinColumn(name = "propertyId", foreignKey = @ForeignKey(name = "fk_reSrcPropDetail_reSrcProp"))
    @JsonIgnore
    private ResourceProperty resourceProperty;
}
