package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "business_type")
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "business_type_seq", allocationSize = 1)
public class BusinessType extends AbstractEntity implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "default_gen"
  )
  private Long id;

  @Column(name = "value_ru")
  private String valueRU;

  @Column(name = "value_kz")
  private String valueKZ;
}

