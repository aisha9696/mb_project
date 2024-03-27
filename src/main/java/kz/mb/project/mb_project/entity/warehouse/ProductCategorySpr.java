package kz.mb.project.mb_project.entity.warehouse;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import javax.persistence.Column;
import kz.mb.project.mb_project.entity.AbstractLanguageSprValue;
import kz.mb.project.mb_project.entity.BusinessTypeSpr;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategorySpr extends AbstractLanguageSprValue implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @Nullable
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonManagedReference
  private ProductCategorySpr parent;

  @JsonProperty(access = Access.READ_ONLY)
  @JsonBackReference
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ProductCategorySpr> subCategory;

  @ManyToMany
  @JoinTable(name = "product_category_business_type",
      joinColumns = @JoinColumn(name = "product_category_id"),
      inverseJoinColumns = @JoinColumn(name = "business_type_id"))
  private List<BusinessTypeSpr> businessType;

  @Enumerated(EnumType.STRING)
  @Column(name ="type")
  private CatalogUnitType type;
}
