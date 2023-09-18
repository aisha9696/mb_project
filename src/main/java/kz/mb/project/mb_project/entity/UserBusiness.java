package kz.mb.project.mb_project.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBusiness {
    @Id
    private UUID id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserDetail user;

    @ManyToOne
    @MapsId("businessId")
    @JoinColumn(name = "business_id")
    private Business business;

    @Column(name = "user_roles")
    private UserRole userRoles;

}