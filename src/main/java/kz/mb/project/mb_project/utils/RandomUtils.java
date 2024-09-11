package kz.mb.project.mb_project.utils;

import java.util.Random;

public class RandomUtils {

  public static String generateRandomString() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomString = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int randomIndex = random.nextInt(characters.length());
      randomString.append(characters.charAt(randomIndex));
    }
    return randomString.toString();
  }

}
