package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ProjectRequest;
import org.example.printmanagement.model.dtos.response.ProjectResponse;
import org.example.printmanagement.model.entities.Project;

import java.util.List;

public interface IProjectService {
    //GET
    List<ProjectResponse> getAll() throws Exception;
    ProjectResponse getById(int projectId) throws Exception;
    //POST
    Project createProject(ProjectRequest req) throws Exception;
    //PUT
    Project editProject(ProjectRequest req) throws Exception;
    //DELETE
    void deleteProject(int projectId) throws Exception;
}
