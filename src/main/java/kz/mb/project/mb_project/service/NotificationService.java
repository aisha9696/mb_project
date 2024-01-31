package kz.mb.project.mb_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final NotificationSender smsService;
  private final NotificationSender emaiService;

  @Autowired
  public NotificationService(
      @Qualifier("smsServiceImpl")
      NotificationSender smsService,
      @Qualifier("emailServiceImpl")
      NotificationSender emaiService) {
    this.smsService = smsService;
    this.emaiService = emaiService;
  }

  public void performSMSNotification(String to, String subject, String message) {
    smsService.sendNotification(to, subject, message);
  }

  public void performEmailNotification(String to, String subject, String message) {
    emaiService.sendNotification(to, subject, message);
  }
}
