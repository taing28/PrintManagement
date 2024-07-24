package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int projectId;
    @Column(insertable = false, updatable = false)
    private int designerId;
    private String filePath;
    private LocalDateTime designTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DesignStatus designStatus;
    private int approverId;

    public Design(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "design", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PrintJob> printJobList;

    @ManyToOne
    @JoinColumn(name = "projectId", foreignKey = @ForeignKey(name = "fk_design_project"))
    @JsonIgnore
    private Project projectDesign;

    @ManyToOne
    @JoinColumn(name = "designerId", foreignKey = @ForeignKey(name = "fk_design_designer"))
    @JsonIgnore
    private User designer;
}
