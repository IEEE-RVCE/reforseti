package org.ieeervce.api.siterearnouveau.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "atokens")
public class AuthToken {
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", updatable = false, nullable = false, referencedColumnName = "uid")
    private User user;
    @Column(unique = true,name="atoken")
    @Id
    private String token;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
