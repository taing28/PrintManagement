package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectResponse {
    private int id;
    private String projectName;
    private String requestDescriptionFromCustomer;
    private LocalDateTime startDate;
    private int employeeId;
    private String employeeName;
    private LocalDateTime expectedEndDate;
    private int customerId;
    private String customerName;
    private String projectStatus;

    public static ProjectResponse toDTO(Project project) {
        ProjectResponse res = new ProjectResponse();
        res.setId(project.getId());
        res.setProjectName(project.getProjectName());
        res.setRequestDescriptionFromCustomer(project.getRequestDescriptionFromCustomer());
        res.setStartDate(project.getStartDate());
        res.setEmployeeId(project.getEmployeeId());
        res.setEmployeeName(project.getEmployeeProject().getFullName());
        res.setExpectedEndDate(project.getExpectedEndDate());
        res.setCustomerId(project.getCustomerId());
        res.setCustomerName(project.getCustomerProject().getFullName());
        res.setProjectStatus(project.getProjectStatus().toString());
        return res;
    }

    public static List<ProjectResponse> toDTO(List<Project> projects) {
        List<ProjectResponse> list = new ArrayList<>();
        for (Project project : projects) {
            list.add(ProjectResponse.toDTO(project));
        }
        return list;
    }
}
