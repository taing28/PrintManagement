package org.example.printmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
    private String email;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();
    private String phoneNumber;
    @Column(insertable = false, updatable = false)
    private int teamId;
    private boolean isActive = true;

    public User(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "teamId", foreignKey = @ForeignKey(name = "fk_user_team"))
    @JsonIgnore
    private Team team;

    @OneToMany(mappedBy = "userNotify", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "employeeBill", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Bill> billList;

    @OneToMany(mappedBy = "userPermiss", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Permission> permissionList;

    @OneToMany(mappedBy = "userKeyPerformance", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<KeyPerformanceIndicators> keyPerformanceIndicatorsList;

    @OneToMany(mappedBy = "employeeCoupon", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ImportCoupon> importCouponList;

    @OneToMany(mappedBy = "designer", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Design> designList;

    @OneToMany(mappedBy = "userFeedback", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<CustomerFeedback> customerFeedbackList;

    @OneToMany(mappedBy = "employeeProject", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Project> projectList;

    @OneToMany(mappedBy = "userToken", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<RefreshToken> tokenList;

    @OneToMany(mappedBy = "userMail", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ConfirmEmail> emailList;
}
