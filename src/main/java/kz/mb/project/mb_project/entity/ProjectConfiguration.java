package kz.mb.project.mb_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "project_configuration", schema = "project_settings")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectConfiguration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "configuration_name")
  private String configurationName;

  @Column(name = "configuration_value")
  private String configurationValue;
}
