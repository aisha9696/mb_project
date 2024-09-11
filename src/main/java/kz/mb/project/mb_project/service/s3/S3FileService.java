package kz.mb.project.mb_project.service.s3;

import java.io.ByteArrayInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kz.mb.project.mb_project.dto.s3.S3FileModel;
import org.apache.commons.codec.binary.Base64;

@Slf4j
@Service
public class S3FileService {

  @Value("${cloud.aws.s3.bucket}")
  private String s3Bucket;

  @Value("${cloud.aws.s3.url}")
  private String s3Url;

  private final AmazonS3 amazonS3Client;

  private final S3ObjectService s3ObjectService;

  @Autowired
  public S3FileService(AmazonS3 amazonS3Client, S3ObjectService s3ObjectService) {
    this.amazonS3Client = amazonS3Client;
    this.s3ObjectService = s3ObjectService;
  }

  /* Загрузка файла в s3
   *
   * Метод принимает строку формата base64 с изображением и код объекта. Перевожу строчное значение
   * в байтовое представление изображения в base64, после чего запускаю входной поток для
   * работы с данными байтового массива. Создаю метаданные объекта s3 и указываю их длинну,
   * равную длине байтового массива base64.
   * С помощью встроенного библиотечного метода (aws s3) putObject(), загружаю изображение.
   */
  public void uploadFile(String b64, String objectkey) {
    byte[] imageByte;
    // BASE64Decoder decoder = new Base64.decodeBase64();
    try {
      imageByte = Base64.decodeBase64(b64);
      ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(imageByte.length);

      PutObjectRequest request = new PutObjectRequest(s3Bucket, objectkey, bis, metadata);
      amazonS3Client.putObject(request);
      bis.close();

    } catch (Exception e) {
      e.printStackTrace();

    }
  }


  /* Удаление объекта из s3
   *
   * Метод вызывает 3 других метода: getObjectKey(), getCheckIfObjectExists(), deleteObject()
   * Сперва получаю ключ объекта через метод getObjectKey(), после чего по этому ключу
   * проверю, есть ли такой объект в s3 getCheckIfObjectExists() и если есть, то удаляю
   * deleteObject(), если такого объекта нет, возвращаю объект с сообщением о том, что такого
   * объекта нет.
   */
  public String delete(String id, String fileName, String path) {

    log.info("ObjectKey = {} ", id, fileName);

    String s3ObjectLink = id + "/" + path + "/" + fileName;

    boolean checkIfObjectExists = s3ObjectService.getCheckIfObjectExists(s3ObjectLink);

    if (checkIfObjectExists) {

      if (deleteObject(s3ObjectLink)) {
        return "Object deleted";
      } else {
        return "Object didn't deleted";
      }

    } else {

      log.info("[S3DELETE] Fail");
      return "Object doesn't exists";
    }
  }

  public S3FileModel upload(S3FileModel s3FileModel, String path) {
    if (!Base64.isBase64(s3FileModel.getFileB64())) {
      s3FileModel.setFileB64("");
      s3FileModel.setMessage("File not in base64 format!");
      return s3FileModel;
    }
    s3FileModel.setStorePath(s3FileModel.getId() + "/" + path + "/" + s3FileModel.getFileName());
    boolean checkIfObjectExists = s3ObjectService.getCheckIfObjectExists(
        s3FileModel.getStorePath());
    if (!checkIfObjectExists) {
      uploadFile(s3FileModel.getFileB64(), s3FileModel.getStorePath());
      s3FileModel.setStorePath(s3Url + "/" + s3Bucket + "/" + s3FileModel.getStorePath());
      s3FileModel.setMessage("Uploaded");
      s3FileModel.setFileB64("");
      log.info("[S3UPLOAD] SUCCESS");
      return s3FileModel;
    } else {
      s3FileModel.setMessage("Object already exists!");
      s3FileModel.setFileB64("");
      log.info("[S3UPLOAD] FAIL");
      return s3FileModel;
    }
  }


  /* Удаление объекта из s3
   *
   * Метод принимает ключ объекта, после чего через бин AmazonS3Client, аутентифицуертся в s3 и
   * с помощью встроенного библиотечного метода(aws s3), deleteObject, удаляется объект.
   * И возвращает true/false, false, в случаях исключений amazon s3
   */
  public boolean deleteObject(String objectKey) throws AmazonServiceException {
    try {
      amazonS3Client.deleteObject(s3Bucket, objectKey);
      log.info("[S3DELETE Object] Object Deleted");
      return true;
    } catch (SdkClientException e) {
      log.error("[S3DELETE Object] Exception: {}", e.getMessage());
      return false;
    }
  }
}
