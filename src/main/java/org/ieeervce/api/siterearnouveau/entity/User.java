package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    Integer userId;

    @Column(name = "sid")
    Integer societyId;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "lastname")
    String lastName;
    
    @Column
    String email;
    
    @Column(name = "pic")
    @ToString.Exclude
    byte[] picture;

    @Column(name = "pwd")
    @ToString.Exclude
    String password;

    @Column
    String role;
}
