package kz.mb.project.mb_project.service;

import jakarta.mail.MessagingException;

@FunctionalInterface
public interface NotificationSender {
  void sendNotification(String to, String subject, String message) ;
}

