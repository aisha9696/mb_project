package kz.mb.project.mb_project.exception;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

public class LanguageException extends RuntimeException {

  public static String getLanguage(ErrorMessage message) {
    if (LocaleContextHolder.getLocale().equals(new Locale("ru"))) {
      return message.getMessageRU();
    } else if (LocaleContextHolder.getLocale().equals(new Locale("kk"))) {
      return message.getMessageKZ();
    } else {
      return message.getMessageKZ() + " "+ message.getMessageRU();
    }
  }

  public LanguageException(ErrorMessage message) {
    super(getLanguage(message));
  }
}
