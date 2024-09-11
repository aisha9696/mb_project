package kz.mb.project.mb_project.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractLanguageSprValue {

  @Column(name = "value_ru")
  private String valueRU;

  @Column(name = "value_kz")
  private String valueKZ;

  /**
   * Признак архивирования.
   */
  @Column(name = "archived")
  private boolean archived;

}
