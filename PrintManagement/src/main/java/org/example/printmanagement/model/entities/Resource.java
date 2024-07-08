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
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resourceName;
    private String image;
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
    private int availableQuantity;
    @Enumerated(EnumType.STRING)
    private ResourceStatus resourceStatus;

    public Resource(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "resource", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<ResourceProperty> propertyList;
}
