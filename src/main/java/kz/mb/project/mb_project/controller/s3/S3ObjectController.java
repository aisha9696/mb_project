package kz.mb.project.mb_project.controller.s3;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import kz.mb.project.mb_project.service.s3.S3ObjectService;

@RestController
@RequestMapping(value = "/s3/partners/objects")
public class S3ObjectController {

  private final S3ObjectService s3ObjectService;

  @Autowired
  public S3ObjectController(S3ObjectService s3ObjectService) {
    this.s3ObjectService = s3ObjectService;
  }


  @GetMapping("/getObjects/{id}")
  public ArrayList<String> getObjects(@PathVariable String id) {
    return s3ObjectService.getObjects(id);
  }

  @GetMapping("/getPhoto/{id}")
  public ArrayList<String> getPhoto(@PathVariable String id) {
    return s3ObjectService.getAllPhoto(id);
  }

  @GetMapping("/deleteFolder/{id}")
  public ResponseEntity<String> deleteFolder(@PathVariable String id) {
    String result = s3ObjectService.deleteFolder(id);
    if (result.equals("Folder doesn't exists!")) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/checkIfObjectExists/{id}/{name}")
  public ResponseEntity<String> checkIfObjectExists(@PathVariable String id, @PathVariable String name) {
    if (s3ObjectService.getCheckIfObjectExists(id + "/" + name)) {
      return ResponseEntity.status(HttpStatus.OK).body("Object exists");
    } else {
      return ResponseEntity.status(HttpStatus.OK).body("Object doesn't exists");
    }
  }

  @GetMapping("/getArchive/{id}/{bin}")
  public void getArchive(HttpServletResponse response, @PathVariable String id, @PathVariable String bin) throws IOException {
    s3ObjectService.ReturnZipObjects(response, id, bin);
  }
}

