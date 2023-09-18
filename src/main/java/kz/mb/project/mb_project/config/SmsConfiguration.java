package kz.mb.project.mb_project.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SmsConfiguration {
  @Value("${sms.url}")
  private String url;
  @Value("${sms.username}")
  private String username;
  @Value("${sms.password}")
  private String password;
  @Value("${sms.originator}")
  private String originator;
}
