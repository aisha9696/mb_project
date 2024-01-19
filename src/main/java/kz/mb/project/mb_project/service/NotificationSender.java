package kz.mb.project.mb_project.service;

@FunctionalInterface
public interface NotificationSender {
  void sendNotification(String to, String subject, String message) ;
}

