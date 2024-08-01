package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ApproveRequest;
import org.example.printmanagement.model.entities.Design;
import org.example.printmanagement.model.services.impl.CloudinaryService;
import org.example.printmanagement.model.services.impl.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/designs")
@CrossOrigin(origins = "*")
public class DesignController {
    @Autowired
    private DesignService _designService;
    @Autowired
    private CloudinaryService _cloudinaryService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<Design> list = _designService.list();
        return ResponseEntity.ok(list.isEmpty() ? list : "There is no list");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Design> designOpt = _designService.getById(id);
        return ResponseEntity.ok(designOpt.isEmpty() ? designOpt.get() : "Design not found");
    }

    @GetMapping("/list")
    public ResponseEntity<?> listByProject(@RequestParam int projectId) {
        try {
           return ResponseEntity.ok(_designService.listByProject(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('DESIGNER')")
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, int designerId, int projectId) throws IOException {
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                return ResponseEntity.badRequest().body("Image non valid!");
            }
            Map result = _cloudinaryService.upload(multipartFile);
            _designService.uploadDesign(projectId, designerId, (String) result.get("url"));
            return ResponseEntity.ok("Upload successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('LEADER')")
    @PutMapping("/approve")
    public ResponseEntity<?> approve(@RequestParam int approverId, int designId, String designStatus) {
        try {
            _designService.confirmDesign(approverId, designId, designStatus);
            return ResponseEntity.ok("Review successfull");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('LEADER')")
    @PutMapping("/approve-list")
    public ResponseEntity<?> approveList(@RequestBody ApproveRequest req) {
        try {
            _designService.confirmDesignList(req.getProjectId(), req.getApproverId(), req.getDesignList(), req.getDesignStatus());
            return ResponseEntity.ok("Set successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('DESIGNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            _designService.deleteDesign(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
