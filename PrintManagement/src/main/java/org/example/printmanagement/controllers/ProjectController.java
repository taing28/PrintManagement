package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ProjectRequest;
import org.example.printmanagement.model.services.impl.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService _projectService;

    //GET METHOD
    /**
     * @method GET
     * @path /projects
     * @return List< Project> - list projects
     */
    @GetMapping()
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok(_projectService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @method GET
     * @path /projects/{id}
     * @param id id of project
     * @return Project - project that match id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(_projectService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //POST
    /**
     * @method POST
     * @path /projects
     * @param req new project request
     * @return Project - project that created
     */
    @PostMapping()
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest req) {
        try {
            return ResponseEntity.ok(_projectService.createProject(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT
    /**
     * @method PUT
     * @path /projects
     * @param req ProjectRequest - project data request
     * @return Project - new project after edit
     */
    @PutMapping()
    public ResponseEntity<?> editProject(@RequestBody ProjectRequest req) {
        try {
            return ResponseEntity.ok(_projectService.editProject(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE
    /**
     * @method DELETE
     * @path /projects/{id}
     * @param id project's id
     * @return result message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int id) {
        try {
            _projectService.deleteProject(id);
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
