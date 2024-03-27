package kz.mb.project.mb_project.service.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class S3ObjectService {

  @Value("${cloud.aws.s3.bucket}")
  private String s3Bucket;

  @Value("${cloud.aws.s3.url}")
  private String s3Url;

  private final AmazonS3 amazonS3Client;

  @Autowired
  public S3ObjectService(AmazonS3 amazonS3Client) {
    this.amazonS3Client = amazonS3Client;
  }

  /*
   * Проверка на наличие объекта в s3 bucket
   */
  public boolean getCheckIfObjectExists(String objectKey) {
    return amazonS3Client.doesObjectExist(s3Bucket, objectKey);
  }

  /* Удаление папки и всего его содержимого
   *
   * Создан пустой ArrayList keys, для хранения ключей.
   * Запускаю цикл, который нужен для записи всех ключей объектов папки в keys, в котором
   * вызывается метод getObjectsFromFolder() и  добавляются ключи объектов в ArrayList key.
   * Во втором цикле удаляю все объекты из папки, через метод deleteObject() и затем удаляю
   * уже саму папку с помощью встроенного метода (aws s3) deleteObject().
   * if-else нужен для определения, было ли что-то в этой папке или нет.
   */
  public String deleteFolder(String id) {
    ArrayList<String> keys = new ArrayList<String>();
    for (int i = 0; i < getObjectsFromFolder(id).size(); i++) {
      keys.add(getObjectsFromFolder(id).get(i));
    }
    for (int i = 0; i < keys.size(); i++) {
      amazonS3Client.deleteObject(s3Bucket, keys.get(i));
    }

    amazonS3Client.deleteObject(s3Bucket, id + "/");
    if (keys.isEmpty()) {
      log.info("[S3DELETE Folder] Doesn't exists!");
      return "Folder doesn't exists!";
    }
    log.info("[S3DELETE Folder] SUCCESS");
    return "Folder Successfully deleted!";
  }

  /* Список всех объектов в папке
   *
   * Метод принимает id (имя папки) и возвращает ArrayList хранящий в себе ключи объектов папки.
   * Здесь создает пустой ArrayList и встроенные методы библиотеки (aws s3) ListObjectsV2Result,
   * List<S3ObjectSummary>, для получения списка всех объектов в папке, после чего записываю
   * ключи объектов в свой пустой ArrayList и передаю его.
   * Фильтрую объекты, то есть проверяю не записывается ли в s3Obj, папку как объект, так
   * как был такой баг.
   */
  public ArrayList<String> getObjectsFromFolder(String path) {
    ArrayList<String> s3Obj = new ArrayList<>();
    ListObjectsV2Result result = amazonS3Client.listObjectsV2(s3Bucket, path + "/");
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    int neededIndex = path.length() + 1;
    for (S3ObjectSummary os : objects) {
      if (os.getKey().substring(neededIndex).length() > 0) {
        s3Obj.add(os.getKey());
      }
    }
    return s3Obj;
  }

  public ArrayList<String> getObjects(String id) {
    ArrayList<String> keys = new ArrayList<>();
    for (int i = 0; i < getObjectsFromFolder(id).size(); i++) {
      keys.add(s3Url + "/" + s3Bucket + "/" + getObjectsFromFolder(id).get(i));
    }
    return keys;
  }

  public ArrayList<String> getAllPhoto(String id) {
    ArrayList<String> keys = new ArrayList<>();
    for (int i = 0; i < getObjectsFromFolder(id + "/Фото").size(); i++) {
      keys.add(s3Url + "/" + s3Bucket + "/" + getObjectsFromFolder(id + "/Фото").get(i));
    }
    return keys;
  }

  public ArrayList<String> getAllDocumentsPath(String id) {
    ArrayList<String> keys = new ArrayList<>();
    for (int i = 0; i < getObjectsFromFolder(id + "/Документы").size(); i++) {
      keys.add(getObjectsFromFolder(id + "/Документы").get(i));
    }
    return keys;
  }

  public void ReturnZipObjects(HttpServletResponse response, String id, String ZipFileName)
      throws IOException {

    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + ZipFileName + ".zip");
    response.setStatus(HttpServletResponse.SC_OK);

    List<String> documents = getAllDocumentsPath(id);
    List<S3ObjectSummary> childObjects = new ArrayList<S3ObjectSummary>();

    // Для каждого пути найти файлы. Накопить их в childObjects
    for (int i = 0; i < documents.size(); i++) {
      ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(s3Bucket)
          .withPrefix(documents.get(i));
      ListObjectsV2Result result = amazonS3Client.listObjectsV2(req);
      childObjects.addAll(result.getObjectSummaries());
    }

    if (childObjects.size() > 0) {
      try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
        for (int i = 0; i < childObjects.size(); i++) {
          if (childObjects.get(i).getSize() > 0) {
            String fileName = childObjects.get(i).getKey();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            S3ObjectInputStream s3IS = amazonS3Client.getObject(s3Bucket,
                childObjects.get(i).getKey()).getObjectContent();
            byte[] byteArray = IOUtils.toByteArray(s3IS);

            ZipEntry e = new ZipEntry(fileName);
            e.setTime(System.currentTimeMillis());

            zippedOut.putNextEntry(e);
            zippedOut.write(byteArray);
            zippedOut.closeEntry();
          }
        }
        zippedOut.finish();
      } catch (Exception e) {
        log.error("ReturnZipObjects Exception: {}", e.getMessage());
      }
    }
  }

}
