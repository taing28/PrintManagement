package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ResourceForPrintJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int resourcePropertyDetailId;
    @Column(insertable = false, updatable = false)
    private int printJobId;

    @ManyToOne
    @JoinColumn(name = "resourcePropertyDetailId", foreignKey = @ForeignKey(name = "fk_reSrcPrintJob_reSrcPropDetail"))
    @JsonIgnore
    private ResourcePropertyDetail resourcePropertyDetailPrint;

    @ManyToOne
    @JoinColumn(name = "printJobId", foreignKey = @ForeignKey(name = "fk_reSrcPrintJob_printJob"))
    @JsonIgnore
    private PrintJob printJob;
}
