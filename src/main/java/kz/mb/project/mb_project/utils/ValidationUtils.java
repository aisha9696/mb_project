package kz.mb.project.mb_project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidationUtils {

  public static boolean validateEmail(String email) {

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pattern = Pattern.compile(emailRegex);

    Matcher matcher = pattern.matcher(email);

    if (!matcher.matches()) {
      return false;
    }
    return true;
  }
}
