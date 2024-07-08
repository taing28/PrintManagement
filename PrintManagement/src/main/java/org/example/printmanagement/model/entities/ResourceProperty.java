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
public class ResourceProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resourcePropertyName;
    @Column(insertable = false, updatable = false)
    private int resourceId;
    private int quantity;

    public ResourceProperty(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "resourceProperty", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ResourcePropertyDetail> resourcePropertyDetailList;

    @ManyToOne
    @JoinColumn(name = "resourceId", foreignKey = @ForeignKey(name = "fk_resourceProperty_resource"))
    @JsonIgnore
    private Resource resource;
}
