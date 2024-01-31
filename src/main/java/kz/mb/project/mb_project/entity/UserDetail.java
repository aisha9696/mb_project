  package kz.mb.project.mb_project.entity;

  import java.io.Serializable;
  import java.util.List;
  import java.util.UUID;

  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  import lombok.Setter;

  import jakarta.persistence.CascadeType;
  import jakarta.persistence.Column;
  import jakarta.persistence.Entity;
  import jakarta.persistence.FetchType;
  import jakarta.persistence.Id;
  import jakarta.persistence.JoinColumn;
  import jakarta.persistence.OneToMany;
  import jakarta.persistence.OneToOne;
  import jakarta.validation.constraints.Email;

  @Getter
  @Setter
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

    @Column(name = "email")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserBusiness> userBusiness;

  }
