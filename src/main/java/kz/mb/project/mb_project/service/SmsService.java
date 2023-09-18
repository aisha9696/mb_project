package kz.mb.project.mb_project.service;

import reactor.core.publisher.Mono;

import kz.mb.project.mb_project.dto.SmsRequest;
import kz.mb.project.mb_project.dto.SmsResponse;

public interface SmsService {
     Mono<SmsResponse> sendSMS(String phone, String message);
}
