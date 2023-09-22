package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс для кеширования а БД смс текстом
 */
@Data
@Entity
@Table(name = "otp")
@AllArgsConstructor
@NoArgsConstructor
public class Otp implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  /**
   * Номер телефона на который был отправлен смс
   */
  @Column(
      name = "phone_number",
      nullable = false
  )
  private String phoneNumber;

  /**
   * Hash текста
   */
  @Column(
      name = "otp_hash",
      nullable = false
  )
  private String otpHash;
  /**
   * Возможность не правильной отправки
   */
  @Column(name = "attempts_available")
  private Integer attemptsAvailable;
  /**
   * Время удаления
   */
  @Column(
      name = "deletion_date",
      nullable = false
  )
  private Date deletionDate;
  /**
   * Текст смс
   */
  @Column(name = "message_text")
  private String messageText;
}