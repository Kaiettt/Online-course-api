package anhkiet.dev.course_management.service;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

import anhkiet.dev.course_management.domain.entity.FileType;
import anhkiet.dev.course_management.domain.entity.Lesson;
import anhkiet.dev.course_management.domain.entity.Material;
import anhkiet.dev.course_management.domain.request.MaterialRequest;
import anhkiet.dev.course_management.error.ResourceExistException;
import anhkiet.dev.course_management.repository.MaterialRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final LessonService lessonService;
    public Material createNewMaterial(MaterialRequest request) throws ResourceExistException,EntityExistsException {
        System.out.println(request);
        try {
            Path path = Paths.get(new URI(request.getUri()));
            if (!Files.exists(path)) {
                throw new ResourceExistException("File not found");
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI or error checking file: " + e.getMessage());
        }
        Lesson lesson = this.lessonService.getLessonById(request.getLessonId());
        if(lesson == null){
            throw new EntityExistsException("Lesson not found");
        }
        Map<String,FileType> typeList = new HashMap<>();
        typeList.put(".pdf",FileType.PDF);
        typeList.put(".docx",FileType.DOCX);
        typeList.put(".txt", FileType.TXT);
        typeList.put(".PNG",FileType.PNG);
        typeList.put("JPG", FileType.JGP);
        typeList.put("JPG", FileType.JPEG);
        FileType fileType = typeList.entrySet().stream()
                .filter(entry -> request.getFileName().toLowerCase().endsWith(entry.getKey().toLowerCase())) // Normalize case
                .findFirst() 
                .map(Map.Entry::getValue) 
                .orElse(null); 
        Material material = Material.builder()
            .name(request.getName())
            .uri(request.getUri())
            .fileName(request.getFileName())
            .folder(request.getFolder())
            .type(fileType)
            .lesson(lesson)
            .build();
        return this.materialRepository.save(material);
    }

}
