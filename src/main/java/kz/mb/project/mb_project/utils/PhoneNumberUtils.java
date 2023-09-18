package kz.mb.project.mb_project.utils;

import org.springframework.lang.Nullable;

public class PhoneNumberUtils {
  @Nullable
  public static String ensureKzCtnWithCountryCode(@Nullable String ctn) {
    if(ctn == null) return null;
    if (ctn.length() == 10) {
      return "7" + ctn;
    } else {
      return "7" +ctn.substring(1,ctn.length()-1);
    }
  }
}
