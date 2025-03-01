package anhkiet.dev.course_management.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import anhkiet.dev.course_management.error.StorageException;

import java.nio.file.Path;
import java.net.URLEncoder;
@Service
public class FileService {

    public void createUploadFolder(String folder) throws URISyntaxException {
    URI uri = new URI(folder);
    Path path = Paths.get(uri);
    File tmpDir = new File(path.toString());
    if (!tmpDir.isDirectory()) {
      try {
        Files.createDirectory(tmpDir.toPath());
        System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + tmpDir.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
    }
  }

  public List<String> store(MultipartFile file, String folder, String basePath)
      throws URISyntaxException, IOException {
    String standardFileName = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8)
                                        .replace("+", "%20");
    String timeMilis = System.currentTimeMillis()+"";
    String finalName = basePath + folder + "/" + timeMilis + "-" + standardFileName;
    URI uri = new URI(finalName);
    Path path = Paths.get(uri);
    try(InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    }
    return Arrays.asList(finalName,timeMilis + "-" + standardFileName);
  }

 public long getFileLength(String folder,String filename)throws URISyntaxException, IOException, StorageException{
    URI uri = new URI(folder + "/" + filename);
    Path path = Paths.get(uri);
    File tmpDir = new File(path.toString());
    if(tmpDir.isDirectory()){
      return 0;
    }
    return tmpDir.length();
  }
    
}
