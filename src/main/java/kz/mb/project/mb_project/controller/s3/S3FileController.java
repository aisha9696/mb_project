package kz.mb.project.mb_project.controller.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kz.mb.project.mb_project.dto.s3.S3FileModel;
import kz.mb.project.mb_project.service.s3.S3FileService;

@RestController
@RequestMapping(value = "/s3/partners/files")
public class S3FileController {

  private final S3FileService s3FileService;

  @Autowired
  public S3FileController(S3FileService s3FileService) {
    this.s3FileService = s3FileService;
  }

  @PostMapping("/upload/photo")
  public ResponseEntity<String> uploadPhoto(@RequestBody S3FileModel s3FileModel) {
    S3FileModel result = s3FileService.upload(s3FileModel, "Фото");
    if (result.getMessage().equals("Object already exists!")) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result.getMessage());
    } else if (result.getMessage().equals("File not in base64 format!")) {
      return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
          .body(result.getMessage());
    }
    return ResponseEntity.status(HttpStatus.OK).body(result.getStorePath());
  }

  @PostMapping("/upload/file")
  public ResponseEntity<S3FileModel> uploadFiles(@RequestBody S3FileModel s3FileModel) {
    S3FileModel result = s3FileService.upload(s3FileModel, "Документы");
    if (result.getMessage().equals("Object already exists!")) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    } else if (result.getMessage().equals("File not in base64 format!")) {
      return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(result);
    }
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/delete/file/{id}/{fileName}")
  public ResponseEntity<String> deleteFile(@PathVariable String id, @PathVariable String fileName) {
    String result = s3FileService.delete(id, fileName, "Документы");
    if (result.equals("Object doesn't exists")) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    } else if (result.equals("Object didn't deleted")) {
      return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(result);
    }
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/delete/photo/{id}/{fileName}")
  public ResponseEntity<String> deletePhoto(@PathVariable String id, @PathVariable String fileName) {
    String result = s3FileService.delete(id, fileName, "Фото");
    if (result.equals("Object doesn't exists")) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    } else if (result.equals("Object didn't deleted")) {
      return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(result);
    }
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
