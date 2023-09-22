package kz.mb.project.mb_project.entity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "file_seq", allocationSize = 1)
public class File implements Serializable {
  @Id
  @Column(name = "id")
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "default_gen"
  )
  private Long id;

  private String name;
  private String extension;

  private byte[] data;

}