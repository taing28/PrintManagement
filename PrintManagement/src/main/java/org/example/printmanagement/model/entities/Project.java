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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String projectName;
    private String requestDescriptionFromCustomer;
    private LocalDateTime startDate = LocalDateTime.now();
    @Column(insertable = false, updatable = false)
    private int employeeId;
    private LocalDateTime expectedEndDate;
    @Column(insertable = false, updatable = false)
    private int customerId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProjectStatus projectStatus;

    public Project(int id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "employeeId", foreignKey = @ForeignKey(name = "fk_project_employee"))
    @JsonIgnore
    private User employeeProject;

    @ManyToOne
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "fk_project_customer"))
    @JsonIgnore
    private Customer customerProject;

    @OneToMany(mappedBy = "projectFeedback", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<CustomerFeedback> customerFeedbackList;

    @OneToMany(mappedBy = "projectDesign", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Design> designList;

    @OneToMany(mappedBy = "projectDelivery", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Delivery> deliveryList;
}
