package kz.mb.project.mb_project.entity;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

  /**
   * Логин создателя
   */
  @Column(name = "created_by")
  private String createdBy;

  /**
   * Дата создания.
   */
  @Column(name = "created_at")
  @CreationTimestamp
  private OffsetDateTime createdAt;

  /**
   * Логин создателя
   */
  @Column(
      name = "updated_by",
      nullable = true
  )
  private String updatedBy;

  /**
   * Дата обновления.
   */
  @Column(name = "updated_at")
  @UpdateTimestamp
  private OffsetDateTime updatedAt;

  /**
   * Признак архивирования.
   */
  @Column(name = "archived")
  private boolean archived;

}

