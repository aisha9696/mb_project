package kz.mb.project.mb_project.entity;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LanguageValueAccessor {

  public String getValueByLanguage(AbstractLanguageSprValue obj) {
    Locale currentLocale = LocaleContextHolder.getLocale();
    if (currentLocale.equals(new Locale("kk"))) {
      return obj.getValueKZ();
    } else if (currentLocale.equals(new Locale("ru"))){
      return obj.getValueRU();
    } else {
      return obj.getValueKZ() + " " + obj.getValueRU();
    }
  }
}
