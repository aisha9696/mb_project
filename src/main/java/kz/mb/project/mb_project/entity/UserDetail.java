package kz.mb.project.mb_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail implements Serializable {

  @Id
  private UUID id;

  private String username;

  @Column(name = "firstname")
  private String firstName;

  @Column(name = "lastname")
  private String lastName;

  @OneToOne
  @JoinColumn(
      name = "photo_id",
      referencedColumnName = "id"
  )
  private File photo;

  @Column(name = "temporal")
  private Boolean temporal;

}
