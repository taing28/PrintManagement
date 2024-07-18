package org.example.printmanagement.model.services;

import org.example.printmanagement.model.dtos.request.ProjectRequest;
import org.example.printmanagement.model.entities.Project;

import java.util.List;

public interface IProjectService {
    //GET
    List<Project> getAll() throws Exception;
    Project getById(int projectId) throws Exception;
    //POST
    Project createProject(ProjectRequest req) throws Exception;
    //PUT
    Project editProject(ProjectRequest req) throws Exception;
    //DELETE
    void deleteProject(int projectId) throws Exception;
}
