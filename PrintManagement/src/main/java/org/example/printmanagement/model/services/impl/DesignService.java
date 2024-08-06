package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.entities.*;
import org.example.printmanagement.model.repositories.DesignRepo;
import org.example.printmanagement.model.repositories.NotificationRepo;
import org.example.printmanagement.model.repositories.PrintJobRepo;
import org.example.printmanagement.model.repositories.ProjectRepo;
import org.example.printmanagement.model.services.IDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class DesignService implements IDesignService {
    @Autowired
    private DesignRepo _designRepo;
    @Autowired
    private NotificationRepo _notificationRepo;
    @Autowired
    private ProjectRepo _projectRepo;
    @Autowired
    private PrintJobRepo _printJobRepo;

    @Override
    public List<Design> list() {
        return _designRepo.findAll();
    }

    @Override
    public Optional<Design> getById(int designId) {
        return _designRepo.findById(designId);
    }

    @Override
    public List<Design> listByProject(int projectId) throws Exception {
        if (!_projectRepo.existsById(projectId)) {
            throw new Exception("Project not found");
        }
        return _designRepo.findAllByProjectId(projectId);
    }

    @Override
    public void uploadDesign(int projectId, int designerId, String imagePath) {
        Design design = new Design();
        //set Designer
        design.setDesignerId(designerId);
        design.setDesigner(new User(designerId));
        //set Project
        design.setProjectId(projectId);
        design.setProjectDesign(new Project(projectId));
        //
        design.setDesignStatus(DesignStatus.REVIEWING);
        design.setDesignTime(LocalDateTime.now());
        design.setFilePath(imagePath);
        _designRepo.save(design);
    }

    @Override
    public void confirmDesign(int projectId, int approverId, int designId, String designStatus) throws Exception {
        Design design = _designRepo.findById(designId).get();
        if (design == null) {
            throw new Exception("Design not found");
        }
        design.setApproverId(approverId);
        //Create notification
        Notification notification = new Notification();
        notification.setUserId(design.getDesignerId());
        notification.setUserNotify(new User(design.getDesignerId()));
        notification.setCreateTime(LocalDateTime.now());
        notification.setLink("/projects/"+projectId+"/designs/");
        notification.setSeen(false);
        //Set designStatus
        switch (designStatus) {
            case "approve":
                design.setDesignStatus(DesignStatus.APPROVED);
                //Send notify
                notification.setContent("Your design have been approved");
                _notificationRepo.save(notification);
                break;
            case "deny":
                design.setDesignStatus(DesignStatus.DENIED);
                //Send notify
                notification.setContent("Your design have been rejected");
                _notificationRepo.save(notification);
                break;
            default:
                throw new Exception("Not choose yet");
        }
        _designRepo.save(design);
    }

    @Override
    public void confirmDesignList(int projectId, int approverId, List<Design> listDesign, String designStatus) throws Exception{
        //Check approver
        Project project = _projectRepo.findById(projectId).get();
        if(project.getEmployeeId() != approverId) {
            throw new Exception("You not own this project");
        }
        project.setProjectStatus(ProjectStatus.PRINTING);
        _projectRepo.save(project);
        //
        if(listDesign.isEmpty()) {
            throw new Exception("There is no design");
        }
        listDesign.forEach(design -> {
            try {
                confirmDesign(projectId, approverId, design.getId(), designStatus);
                //Create print job
                PrintJob printJob = new PrintJob();
                printJob.setDesignId(design.getId());
                printJob.setDesign(new Design(design.getId()));
                printJob.setPrintJobStatus(PrintJobStatus.PENDING);
                _printJobRepo.save(printJob);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }

    @Override
    public void deleteDesign(int designId) throws Exception {
        if (!_designRepo.existsById(designId)) {
            throw new Exception("Design not found");
        }
        _designRepo.deleteById(designId);
    }
}
