package kz.mb.project.mb_project.entity.warehouse;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  private ProductCategory category;

  @OneToOne
  private ProductCategory subCategory;

  private String productName;

  private String description;

  private String protoLink;

  private String barcode;

  private Boolean barcodeLocked;

  @ManyToOne
  private Brand brand;
}
