package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="users")
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 100L;
    @Id
    @Column(name = "uid")
    private Integer userId;

    // TODO update the modelling to include the other table
    @Column(name = "sid")
    private Integer societyId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;
    
    @Column
    private String email;
    
    @Column(name = "pic")
    @ToString.Exclude
    private byte[] picture;

    @Column(name = "pwd")
    @ToString.Exclude
    private String password;

    @Column
    private String role;
}
