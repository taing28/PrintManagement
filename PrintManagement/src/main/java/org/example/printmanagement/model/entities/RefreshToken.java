package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private int userId;
    private String token;
    private LocalDateTime ExpiryTime;

    @ManyToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_token_user"))
    @JsonIgnore
    private User userToken ;
}
