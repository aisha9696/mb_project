package kz.mb.project.mb_project.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class AbstractLanguageValue extends AbstractEntity {

  @Column(name = "value_ru")
  private String valueRU;

  @Column(name = "value_kz")
  private String valueKZ;

}
