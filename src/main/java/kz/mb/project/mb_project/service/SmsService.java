package kz.mb.project.mb_project.service;

import reactor.core.publisher.Mono;
import kz.mb.project.mb_project.dto.SmsResponse;

public interface SmsService  extends NotificationService{
     Mono<SmsResponse> sendSMS(String phone, String message);
}
