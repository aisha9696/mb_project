package kz.mb.project.mb_project.entity.warehouse;


import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.AbstractEntity;

//@Entity
//@Table(name = "contractor")
@Getter
@Setter
public class Contractor extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * Название котрагента
   * */
  private String name;

  /**
   * Номер телефона
   * */
  private String phoneNumber;

  /**
   * Главный контрагент
   * */
  private Boolean mainContractor;

  /**
   * Срок доставки
   * */
  private String estimatedDeliveryTime;

  /**
   * Себестоимость
   * */
  private Float primeCost;

  /**
   * Продажная цена
   * */
  private Float salePrice;

  /**
   * Маржинальность
   * */
  private Float margin;

  /**
   * Наценка
   * */
  private Float markup;

  /**
   * Оптом
   * */
  private Boolean wholesale;

  /**
   * Оптом продажная цена
   * */
  private Float wholesalePrice;
  /**
   * Оптом наценка
   * */
  private Float wholesaleMarkup;

  /**
   * Минимальное к-во для перехода оптовой продажи
   * */
  private Integer minCountToWholesale;

}
