package org.example.printmanagement.model.services;

import org.example.printmanagement.model.entities.Design;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IDesignService {
    List<Design> list();

    Optional<Design> getById(int designId);

    void uploadDesign(int projectId, int designerId, String imagePath);

    void confirmDesign(int approverId, int designId, String designStatus) throws Exception;

    void deleteDesign(int designId) throws Exception;
}
