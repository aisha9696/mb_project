package kz.mb.project.mb_project.entity.warehouse;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

//@Entity
//@Table(name = "product")
@Getter
@Setter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  private ProductCategorySpr category;

  private String productName;

  private String description;

  private String protoLink;

  private String barcode;

  private Boolean barcodeLocked;

  @ManyToOne
  private Brand brand;
}
