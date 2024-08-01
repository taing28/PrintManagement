package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class KeyPerformanceIndicators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int employeeId;
    private String indicatorName;
    private int target;
    private int actuallyAchieved;
    @Enumerated(EnumType.STRING)
    private Period period;
    private boolean achieveKPI;

    @ManyToOne
    @JoinColumn(name = "employeeId", foreignKey = @ForeignKey(name = "fk_keyPerformance_user"))
    @JsonIgnore
    private User userKeyPerformance;
}
