package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atokens")
@Data
public class AuthToken {
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", updatable = false, nullable = false, referencedColumnName = "uid")
    private User user;
    @Column(unique = true,name="atoken")
    @Id
    private String token;
}
