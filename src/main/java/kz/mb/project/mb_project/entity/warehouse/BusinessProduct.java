package kz.mb.project.mb_project.entity.warehouse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kz.mb.project.mb_project.entity.Business;

//@Entity
//@Table(name = "business_product")
@Getter
@Setter
public class BusinessProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Business business;

    @OneToMany
    private List<ProductStandart> standarts;

    @OneToMany
    private List<Contractor> contractors;

}
