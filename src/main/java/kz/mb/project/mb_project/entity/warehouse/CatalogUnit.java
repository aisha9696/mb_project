package kz.mb.project.mb_project.entity.warehouse;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.AbstractLanguageValue;

@Entity
@Table(name = "catalog_unit")
@Getter
@Setter
public class CatalogUnit extends AbstractLanguageValue implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

}
