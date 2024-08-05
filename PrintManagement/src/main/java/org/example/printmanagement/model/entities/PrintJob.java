package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PrintJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int designId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PrintJobStatus printJobStatus;

    public PrintJob(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "printJob", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ResourceForPrintJob> resourceForPrintJobList;

    @ManyToOne
    @JoinColumn(name = "designId", foreignKey = @ForeignKey(name = "fk_printJob_design"))
    @JsonIgnore
    private Design design;
}
