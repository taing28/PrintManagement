package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CustomerFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int projectId;
    @Column(insertable = false, updatable = false)
    private int customerId;
    private String feedbackContent;
    private String responseByCompany;
    @Column(insertable = false, updatable = false)
    private int userFeedbackId;
    private LocalDateTime feedbackTime = LocalDateTime.now();
    private LocalDateTime responseTime;

    @ManyToOne
    @JoinColumn(name = "projectId", foreignKey = @ForeignKey(name = "fk_feedback_project"))
    @JsonIgnore
    private Project projectFeedback;

    @ManyToOne
    @JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "fk_feedback_customer"))
    @JsonIgnore
    private Customer customerFeedback;

    @ManyToOne
    @JoinColumn(name = "userFeedbackId", foreignKey = @ForeignKey(name = "fk_feedback_user"))
    @JsonIgnore
    private User userFeedback;
}
