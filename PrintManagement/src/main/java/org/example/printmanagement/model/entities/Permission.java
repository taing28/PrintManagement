package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int userId;
    @Column(insertable = false, updatable = false)
    private int roleId;

    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_permission_user"))
    @JsonIgnore
    private User userPermiss;

    @ManyToOne
    @JoinColumn(name = "roleId", foreignKey = @ForeignKey(name = "fk_permission_role"))
    @JsonIgnore
    private Role rolePermiss;
}
