package kz.mb.project.mb_project.utils;

public class IdUtils {

  public static String getIDFromUrl(String url) {
    String[] uidParts = url.split("/");
    return uidParts[uidParts.length - 1];
  }
}
