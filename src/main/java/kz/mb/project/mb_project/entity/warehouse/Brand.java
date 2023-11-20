package kz.mb.project.mb_project.entity.warehouse;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.AbstractEntity;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "brand_name")
  private String name;

  @ManyToOne
  private ProductCategory category;
}
