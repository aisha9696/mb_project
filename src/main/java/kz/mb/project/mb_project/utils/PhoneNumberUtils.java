package kz.mb.project.mb_project.utils;

import java.util.regex.Pattern;
import org.springframework.lang.Nullable;

public class PhoneNumberUtils {
  static String kzPhoneNum [] = new String[]{
     "^\\+?77([0124567][0-8]\\d{7})$"
  };
  @Nullable
  public static String ensureKzCtnWithCountryCode(@Nullable String ctn) {
    if(ctn == null) return null;
    boolean isPhoneNum = false;
    for(String pattern : kzPhoneNum){
      if(Pattern.matches(pattern,ctn)){
        isPhoneNum = true;
        break;
      }
    }
    if(isPhoneNum){
      if (ctn.length() == 10) {
        return "8" + ctn;
      } else {
        return "8" +ctn.substring(1,ctn.length()-1);
      }
    }
    return null;
  }
}
