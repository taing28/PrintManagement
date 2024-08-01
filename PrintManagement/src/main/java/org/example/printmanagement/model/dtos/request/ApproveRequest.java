package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Design;

import java.util.List;

@Data
@NoArgsConstructor
public class ApproveRequest {
    private int projectId;
    private int approverId;
    private List<Design> designList;
    private String designStatus;
}
