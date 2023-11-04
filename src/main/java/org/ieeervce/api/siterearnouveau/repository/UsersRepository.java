package org.ieeervce.api.siterearnouveau.repository;

import org.ieeervce.api.siterearnouveau.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Integer> {
    
}
