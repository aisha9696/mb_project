package kz.mb.project.mb_project.entity.warehouse;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.AbstractLanguageSprValue;


/**
 * Справочник каталога виде размера S, XS, M, L, XL
 * г масса размер
 *
 * */
@Entity
@Table(name = "catalog_unit", schema = "project_settings")
@Getter
@Setter
public class CatalogUnitSpr extends AbstractLanguageSprValue implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name ="type")
  private CatalogUnitType type;

}
