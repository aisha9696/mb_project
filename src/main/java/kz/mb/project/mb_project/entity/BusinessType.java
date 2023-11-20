package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.warehouse.ProductCategory;


@Entity
@Table(name = "business_type")
@Getter
@Setter
public class BusinessType extends AbstractLanguageValue implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToMany
  @JoinTable(name = "product_category_business_type",
      joinColumns = @JoinColumn(name = "business_type_id"),
      inverseJoinColumns = @JoinColumn(name = "product_category_id"))
  private List<ProductCategory> productCategories;

}

