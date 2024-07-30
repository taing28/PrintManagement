package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.dtos.request.ProjectRequest;
import org.example.printmanagement.model.entities.*;
import org.example.printmanagement.model.repositories.PermissionRepo;
import org.example.printmanagement.model.repositories.ProjectRepo;
import org.example.printmanagement.model.repositories.RoleRepo;
import org.example.printmanagement.model.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepo _projectRepo;
    @Autowired
    private PermissionRepo _permissionRepo;
    @Autowired
    private RoleRepo _roleRepo;

    @Override
    public List<Project> getAll() throws Exception {
        List<Project> projects = _projectRepo.findAll();
        if (projects.isEmpty()) {
            throw new Exception("There is no project");
        }
        return projects;
    }

    @Override
    public Project getById(int projectId) throws Exception {
        Project project = _projectRepo.findById(projectId).get();
        if (project == null) {
            throw new Exception("Project not available");
        }
        return project;
    }

    @Override
    public Project createProject(ProjectRequest req) throws Exception {
        Project project = req.toEntity();
        //Check if Project name existed
        if (_projectRepo.existsByProjectNameEqualsIgnoreCase(project.getProjectName())) {
            throw new Exception("Project name already exist");
        }
        //Check if deadline too soon or in the pass
        if (project.getExpectedEndDate().isBefore(LocalDateTime.now().plusDays(1))) {
            throw new Exception("Project end date must be after current time at least one day");
        }
        project.setProjectStatus(ProjectStatus.DESIGNING);
        //Check if employee was not a leader role
        Role roleEmployee = _roleRepo.findRoleByRoleCodeEquals("LEADER");
        if (_permissionRepo.findPermissionByUserIdAndRoleId(project.getEmployeeId(), roleEmployee.getId()) == null) {
            Permission permission = new Permission();
            permission.setUserId(project.getEmployeeId());
            permission.setUserPermiss(new User(project.getEmployeeId()));
            permission.setRoleId(roleEmployee.getId());
            permission.setRolePermiss(new Role(roleEmployee.getId()));
            _permissionRepo.save(permission);
        }
        return _projectRepo.save(project);
    }

    @Override
    public Project editProject(ProjectRequest req) throws Exception {
        Project project = req.toEntity();
        Project oldProject = _projectRepo.findById(project.getId()).get();
        project.setStartDate(oldProject.getStartDate());
        //Check if Project name existed
        if (_projectRepo.existsByProjectNameEqualsIgnoreCase(project.getProjectName()) && !oldProject.getProjectName().equals(project.getProjectName())) {
            throw new Exception("Project name already exist");
        }
        //Check if deadline too soon or in the pass
        if (project.getExpectedEndDate().isBefore(LocalDateTime.now().plusDays(1))) {
            throw new Exception("Project end date must be after current time at least one day");
        }
        switch (req.getProjectStatus()) {
            case "design":
                project.setProjectStatus(ProjectStatus.DESIGNING);
                break;
            case "print":
                project.setProjectStatus(ProjectStatus.PRINTING);
                break;
            case "done":
                project.setProjectStatus(ProjectStatus.DONE);
                break;
            default:
        }
        return _projectRepo.save(project);
    }

    @Override
    public void deleteProject(int projectId) throws Exception {
        if (!_projectRepo.existsById(projectId)) {
            throw new Exception("Project not exited");
        }
        _projectRepo.deleteById(projectId);
    }
}
