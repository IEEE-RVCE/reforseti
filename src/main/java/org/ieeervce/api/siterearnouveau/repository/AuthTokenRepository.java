package org.ieeervce.api.siterearnouveau.repository;

import org.ieeervce.api.siterearnouveau.entity.AuthToken;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken,String> {
    @Query(value = "SELECT count(*)>0 from atokens where uid=?1 and atoken=?2",nativeQuery = true)
    boolean existsByUidAndToken(Integer uid,String token);

    Optional<AuthToken> findOneByToken(String token);

    boolean deleteByToken(String token);

    boolean deleteByUser(User user);

}
