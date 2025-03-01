package anhkiet.dev.course_management.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import anhkiet.dev.course_management.domain.responce.RestUploadFile;
import anhkiet.dev.course_management.error.StorageException;
import anhkiet.dev.course_management.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Files;
import java.nio.file.Path;
@RestController
@RequestMapping("/api/v1")
public class FileController {
  @Value("${ak.upload-file.base-uri}")
  private String basePath;

  private final FileService fileService;

  public FileController(FileService fileService){
    this.fileService = fileService;
  }
   @PostMapping("/files")
  public ResponseEntity<RestUploadFile> upload(
      @RequestParam("file") MultipartFile file, @RequestParam("folder") String folder)
      throws URISyntaxException, IOException, StorageException {
    if (file == null || file.isEmpty()) {
      throw new StorageException("File is empty");
    }
    String originFileName = file.getOriginalFilename();
    List<String> allowedExtensions = Arrays.asList(".pdf", ".jpeg", ".png", "jpg", ".docx", ".doc");
    boolean isValid = allowedExtensions.stream().anyMatch(item -> originFileName.endsWith(item));
    if (!isValid) {
      throw new StorageException(
          "File format is not valid, please choose another file or convert to proper format");
    }
    fileService.createUploadFolder(basePath + folder);
    List<String> filePath = fileService.store(file, folder, basePath);
    RestUploadFile res = new RestUploadFile();
    res.setUri(filePath.get(0));
    res.setCreatedAt(Instant.now());
    res.setFolder(folder);
    res.setFileName(filePath.get(1));
    res.setName(originFileName);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  @PostMapping("/files/mutiple")
  public ResponseEntity<List<RestUploadFile>> upload(
      @RequestParam("file") MultipartFile[] files, @RequestParam("folder") String folder)
      throws URISyntaxException, IOException, StorageException {
    if (files == null || files.length == 0) {
      throw new StorageException("File is empty");
    }
    List<String> allowedExtensions = Arrays.asList(".pdf", ".jpeg", ".png", "jpg", ".docx", ".doc");
    List<RestUploadFile> RestUploadFile = new ArrayList<>();
    fileService.createUploadFolder(basePath + folder);
    for (MultipartFile file : files) {
        String originFileName = file.getOriginalFilename();
    
    boolean isValid = allowedExtensions.stream().anyMatch(item -> originFileName.endsWith(item));
        if (!isValid) {
        throw new StorageException(
            "File format is not valid, please choose another file or convert to proper format");
        }
        List<String> filePath = fileService.store(file, folder, basePath);
        RestUploadFile res = new RestUploadFile();
        res.setUri(filePath.get(0));
        res.setCreatedAt(Instant.now());
        res.setFolder(folder);
        res.setFileName(filePath.get(1));
        res.setName(originFileName);
        RestUploadFile.add(res);
    }
    
    return ResponseEntity.status(HttpStatus.CREATED).body(RestUploadFile);
  }


 
  @GetMapping("/files")
public ResponseEntity<Resource> downloadFile(
    @RequestParam("filename") String filename,
    @RequestParam("folder") String folder
) throws StorageException, URISyntaxException, IOException {
    System.out.println(filename);
    if (filename == null || filename.isEmpty()) {
        throw new StorageException("Missing required params");
    }

    try {
        Path filePath = Paths.get("D:/upload/")
            .resolve(folder + "/" + filename)
            .normalize();
        Resource resource = new FileSystemResource(filePath.toFile());
        if (!resource.exists() || !resource.isReadable()) {
            throw new StorageException("File not found or not readable: " + filename);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, 
                "attachment; filename=\"" + resource.getFilename() + "\"")
            .header(HttpHeaders.CONTENT_TYPE, contentType)
            .body(resource);
    } catch (Exception e) {
        throw new StorageException("Could not load file: " + filename);
    }
}
}
