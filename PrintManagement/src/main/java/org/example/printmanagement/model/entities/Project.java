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
    private ProjectStatus projectStatus;

    public Project(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "", foreignKey = @ForeignKey(name = ""))
    @JsonIgnore
    private ;

    @ManyToOne
    @JoinColumn(name = "", foreignKey = @ForeignKey(name = ""))
    @JsonIgnore
    private ;

    @OneToMany(mappedBy = "", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<> ;

    @OneToMany(mappedBy = "", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<> ;

    @OneToMany(mappedBy = "", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<> ;
}
