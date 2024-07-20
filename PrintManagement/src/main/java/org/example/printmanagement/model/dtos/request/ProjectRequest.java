package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Customer;
import org.example.printmanagement.model.entities.Project;
import org.example.printmanagement.model.entities.ProjectStatus;
import org.example.printmanagement.model.entities.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectRequest {
    private int id;
    private String projectName;
    private String requestDescriptionFromCustomer;
    private LocalDateTime expectedEndDate;
    private int employeeId;
    private int customerId;
    private String projectStatus; //design, print, done

    public Project toEntity() {
        Project project = new Project();
        if (this.id != 0) { //Old project
            project.setId(this.id);
        }
        if (this.id == 0) { //New project
            project.setStartDate(LocalDateTime.now());
        }
        project.setProjectName(this.projectName);
        project.setRequestDescriptionFromCustomer(this.requestDescriptionFromCustomer);
        project.setExpectedEndDate(this.expectedEndDate);
        project.setEmployeeId(this.employeeId);
        project.setEmployeeProject(new User(this.employeeId));
        project.setCustomerId(this.customerId);
        project.setCustomerProject(new Customer(this.customerId));
        return project;
    }
}