package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ResourceRequest;
import org.example.printmanagement.model.services.impl.CloudinaryService;
import org.example.printmanagement.model.services.impl.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService _resourceService;
    @Autowired
    private CloudinaryService _cloudinaryService;

    //GET METHOD
    @GetMapping()
    private ResponseEntity<?> getAll() {
        return ResponseEntity.ok(_resourceService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(_resourceService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //POST METHOD
    @PostMapping()
    private ResponseEntity<?> create(@RequestParam MultipartFile multipartFile, String name, String resourceType, int availableQuantity, String resourceStatus) throws IOException {
        try {
            ResourceRequest req = new ResourceRequest(name, resourceType, availableQuantity, resourceStatus);
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                return ResponseEntity.badRequest().body("Image non valid!");
            }
            Map result = _cloudinaryService.upload(multipartFile);
            _resourceService.createResource((String) result.get("url"), req);
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT METHOD
    @PutMapping()
    private ResponseEntity<?> update(@RequestBody ResourceRequest req) {
        try {
            _resourceService.updateResource(req);
            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable int id){
        try {
            _resourceService.deleteResource(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
