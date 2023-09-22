package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 * Сущность для бизнеса
 */
@Entity
@Table(name = "business")
@Getter
@Setter
public class Business extends AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private String address;

  @OneToOne
  private BusinessType businessType;

  @OneToMany
  private Set<UserBusiness> users = new HashSet<>();

  @Enumerated(EnumType.STRING)
  @Column(
      name = "payment_types",
      columnDefinition = "text[]"
  )
  private List<PaymentType> paymentTypes;

}
