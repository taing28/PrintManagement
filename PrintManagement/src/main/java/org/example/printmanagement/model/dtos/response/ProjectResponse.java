package org.example.printmanagement.model.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.*;

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
    private User employee;
    private LocalDateTime expectedEndDate;
    private Customer customer;
    private String projectStatus;
    private List<Design> designList;
    private List<Delivery> deliveryList;
    private List<CustomerFeedback> customerFeedbackList;

    public static ProjectResponse toDTO(Project project) {
        ProjectResponse res = new ProjectResponse();
        res.setId(project.getId());
        res.setProjectName(project.getProjectName());
        res.setRequestDescriptionFromCustomer(project.getRequestDescriptionFromCustomer());
        res.setStartDate(project.getStartDate());
        res.setEmployee(project.getEmployeeProject());
        res.setExpectedEndDate(project.getExpectedEndDate());
        res.setCustomer(project.getCustomerProject());
        res.setProjectStatus(project.getProjectStatus().toString());
        res.setDesignList(project.getDesignList());
        res.setDeliveryList(project.getDeliveryList());
        res.setCustomerFeedbackList(project.getCustomerFeedbackList());
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
