package kz.mb.project.mb_project.service;


import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import kz.mb.project.mb_project.config.SmsConfiguration;
import kz.mb.project.mb_project.dto.SmsRequest;
import kz.mb.project.mb_project.dto.SmsResponse;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.InternalServerException;
import kz.mb.project.mb_project.exception.InvalidRequestException;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService{

  protected final SmsConfiguration smsConfiguration;
  WebClient webClient;

  public SmsServiceImpl(SmsConfiguration smsConfiguration) {
    this.smsConfiguration = smsConfiguration;
    this.webClient = WebClient.create();
  }


  @Override
  public void sendNotification(String to, String subject,String message) {
    String token = smsConfiguration.getUsername() + ":" + smsConfiguration.getPassword();
    String encodedClientData =
        Base64.getEncoder().encodeToString(token.getBytes());
    SmsRequest request = SmsRequest.builder().from(smsConfiguration.getOriginator()).to(to).text(message).build();
    webClient.post()
        .uri(smsConfiguration.getUrl())
        .header("Authorization", "Basic " + encodedClientData)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(request), SmsRequest.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          return Mono.just(
              new InvalidRequestException(ErrorMessage.INVALID_PHONE_NUMBER));
        })
        .onStatus(HttpStatusCode::is5xxServerError, response -> {
          return Mono.just(
              new InternalServerException(ErrorMessage.SMS_SENDING_ERROR));
        })
        .bodyToMono(SmsResponse.class).block();
  }
}
