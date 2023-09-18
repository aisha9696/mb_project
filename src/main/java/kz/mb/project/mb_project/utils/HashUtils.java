package kz.mb.project.mb_project.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtils {

  private static MessageDigest md;

  private HashUtils() {}

  static {
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String hash(String str) {
    byte[] arr = md.digest(str.getBytes());
    return Base64.getEncoder().encodeToString(arr);
  }
}