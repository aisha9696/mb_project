package kz.mb.project.mb_project.entity;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

  /**
   * Логин создателя
   */
  @OneToOne
  private UserDetail createdBy;

  /**
   * Дата создания.
   */
  @Column(name = "created_at")
  @CreationTimestamp
  private OffsetDateTime createdAt;

  /**
   * Логин создателя
   */
  @OneToOne
  private UserDetail updatedBy;

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

