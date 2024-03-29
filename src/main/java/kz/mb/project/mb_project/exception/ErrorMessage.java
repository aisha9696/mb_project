package kz.mb.project.mb_project.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
  INVALID_EMAIL("Не правильный email, введите корректный email", "Қате email, қайта теріңіз "),
  USER_NOT_FOUND_EXCEPTION("Пользователь не существует!", "Қолданушы тіркелмеген!"),
  USER_FOUND_EXCEPTION("Пользователь с данным номером существует!", "Осындай номердегі қолданушы тіркелген!"),
  USER_CREATE_EMAIL_EXCEPTION("Пользователь с данным email существует!","Осындай email қолданушы тіркелген!"),
  USER_CREATE_EXCEPTION("Вы ввели некорректные данные для создания пользователя ","Қолданушыны тіркеу сәтірде қате! "),
  USER_UPDATE_EXCEPTION("Невозможно изменить пользователя","Қолданушыны өзгерттуде қате! "),
  USER_DELETE_EXCEPTION("Невозможно удалить пользователя","Қолданушыны өшіруге болмайды! "),
  USER_SET_PASSWORD_EXCEPTION("Ошибка при смене пароля от пользователя","Қолданушының паролін өзгерту кезінде қате"),
  INCORRECT_PASSWORD("Не правильный пароль ","Қате пароль!"),
  INVALID_USER("Пользователь не валидный либо обновите пароль или валидируйте номер через смс","Қате пароль!"),
  REFRESH_TOKEN_EXPIRED("Время refresh token истек! ","refresh token уакыты бітті"),
  REFRESH_TOKEN_EXCEPTION("Ошибка refresh token","refresh token қате"),
  INCORRECT_USER("Пользователь некорректный!","Пользователь некорректный!"),
  OTP_NOT_VERIFIED("Верификация смс не прошла! Пройдите Верификацию ","Верификация смс жасалмады!"),
  AUTHORIZATION_ERROR("Ошибка авторизации Admin","Авторизацияда қате!"),
  INVALID_REFRESH_TOKEN("Не валидный токен!","Токен валидациядан отпеді"),
  USER_PASSWORD_OLD_NEW_IS_NOT_MATCH("Новый и старый пароль не совпадает!","Жаңа және ескі парольдар сәйкес келмейді!"),
  KEYCLOAK_ADMIN_AUTH_ERROR("Ошибка доступа админа при изменении данных о пользователе! ru", "Ошибка доступа админа при изменении данных о пользователе! kz"),
  OTP_SENDED("СМС был отправлен!","SMS жіберілді!"),
  OTP_EXPIRED("Время кода истекло","OTP верификация уақыты өтті!"),
  OTP_COUNT_EXPIRED("Превышено количество попыток подтверждения действия OTP кодом","OTP тексеру мөлшері аяқталды, sms-ті қайтып жіберіңіз!"),
  INVALID_OTP("Введен неверный код","OTP қате терілді!"),
  INTERNAL_SMS_ERROR("Ошибка при отправки sms!","SMS жіберуде қате!"),
  INVALID_PHONE_NUMBER("Не правильный номер телефона!","Телефон номер қате!"),
  SMS_SENDING_ERROR("Ошибка при отправке смс","Ошибка при отправке смс"),
  CREATE_INCORRECT_PHONE_NUMBER("Не правильный номер телефона","Телефон номері қате"),
  KEYCLOAK_SERVER_EXCEPTION("Ошибка на стороне авторизационного сервиса!","Ошибка на стороне авторизационного сервиса!"),
  USER_LOCKED("Пользователь заблокирован из-за превышения допустимого числа авторизаций","Пользователь заблокирован из-за превышения допустимого числа авторизаций kz"),
  BUSINESS_NOT_FOUND_EXCEPTION("Данный бизнес не найден", "Бұнадай бизнес табылмады!"),
  OWNER_NOT_FOUND_EXCEPTION("Владелец не был найден для создания сотрудника", ""),
  PROJECT_CONFIGURATION_IS_NOT_FOUND("Конфигурации проекта не были найдены","Проект конфигурациялары табылмады"),
  BUSINESS_COUNT_EXCEED_EXCEPTION("Не возможно создать больше бизнеса","Aртық бизнес құра алмайсыз! "),
  EMPLOYEE_COUNT_EXCEED_EXCEPTION("Не возможно создать больше пользователей","Aртық жұмысшы тіркей алмайсыз!"),
  MEMBER_FOUND_EXCEPTION("Вы уже зарегистрировали пользователя в данный филиал","Вы уже зарегистрировали пользователя в данный филиал");
  private final String messageRU;
  private final String messageKZ;

  ErrorMessage(String messageRU, String messageKZ) {
    this.messageRU = messageRU;
    this.messageKZ = messageKZ;
  }
}
