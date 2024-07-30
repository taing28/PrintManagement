package org.example.printmanagement.model.services;

import org.example.printmanagement.model.entities.Design;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IDesignService {
    List<Design> list();

    Optional<Design> getById(int designId);

    List<Design> listByProject(int projectId) throws Exception;

    void uploadDesign(int projectId, int designerId, String imagePath);

    void confirmDesign(int approverId, int designId, String designStatus) throws Exception;

    void confirmDesignList(int projectId, int approverId, List<Design> listDesign, String designStatus) throws Exception;

    void deleteDesign(int designId) throws Exception;
}
