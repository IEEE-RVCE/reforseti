package org.ieeervce.api.siterearnouveau.repository;

import org.ieeervce.api.siterearnouveau.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocietiesRepository extends JpaRepository<Society,Integer> {
    Optional<Society> findByActualSocietyId(short actualSocietyId);
    void deleteByActualSocietyId(short actualSocietyId);
}
