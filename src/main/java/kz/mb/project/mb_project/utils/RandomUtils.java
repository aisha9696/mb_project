package kz.mb.project.mb_project.utils;

import java.util.Random;

public class RandomUtils {
  public static String generateRandomString() {
    // Characters to include in the random string
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Create a StringBuilder to store the generated string
    StringBuilder randomString = new StringBuilder();

    // Create a Random object to generate random indices
    Random random = new Random();

    // Generate the random string
    for (int i = 0; i < 10; i++) {
      // Get a random index within the range of characters
      int randomIndex = random.nextInt(characters.length());

      // Append the character at the random index to the StringBuilder
      randomString.append(characters.charAt(randomIndex));
    }

    return randomString.toString();
  }

}
