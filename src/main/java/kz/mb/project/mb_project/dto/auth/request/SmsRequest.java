package kz.mb.project.mb_project.dto.auth.request;

import lombok.Builder;

@Builder
public record SmsRequest(String from, String to, String text) {
  // Конструктор, методы доступа (accessors), equals и hashCode создаются автоматически
}