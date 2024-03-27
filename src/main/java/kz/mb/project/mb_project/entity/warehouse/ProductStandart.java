package kz.mb.project.mb_project.entity.warehouse;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javax.persistence.Column;

//@Entity
//@Table(name = "product_standart")
@Getter
@Setter
public class ProductStandart {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name ="unit")
  @OneToOne
  private CatalogUnitSpr unit;

  @Column(name ="standart_quantity")
  private Integer standartQuantity;

  @Column(name ="min_balance")
  private Integer minBalance;

}
