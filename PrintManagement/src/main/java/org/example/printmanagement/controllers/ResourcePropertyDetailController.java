package org.example.printmanagement.controllers;

import org.example.printmanagement.model.dtos.request.ResourcePropertyDetailRequest;
import org.example.printmanagement.model.services.impl.CloudinaryService;
import org.example.printmanagement.model.services.impl.ResourcePropertyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/property-detail")
public class ResourcePropertyDetailController {
    @Autowired
    private ResourcePropertyDetailService _propertyDetailService;
    @Autowired
    private CloudinaryService _cloudinaryService;

    //GET METHOD
    @GetMapping()
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(_propertyDetailService.list());
    }

    //POST METHOD
    @PostMapping()
    public ResponseEntity<?> create(@RequestParam MultipartFile multipartFile, int propertyDetailId, BigDecimal price, String propertyDetailName, int propertyId, int quantity) throws IOException {
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                return ResponseEntity.badRequest().body("Image non valid!");
            }
            Map result = _cloudinaryService.upload(multipartFile);
            _propertyDetailService.create(new ResourcePropertyDetailRequest(propertyDetailId, (String) result.get("url"), price, propertyDetailName, propertyId, quantity));
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT METHOD
    @PutMapping("/update-quantity")
    public ResponseEntity<?> updateQuantity(@RequestParam int propertyDetailId, int quantity) {
        try {
            _propertyDetailService.updateQuantity(propertyDetailId, quantity);
            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE METHOD
    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            _propertyDetailService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
