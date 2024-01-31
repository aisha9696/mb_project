package kz.mb.project.mb_project.entity;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "business_type")
@Getter
@Setter
public class BusinessType extends AbstractLanguageValue implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

}

