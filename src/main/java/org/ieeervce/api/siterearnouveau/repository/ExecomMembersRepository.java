package org.ieeervce.api.siterearnouveau.repository;

import java.time.LocalDate;
import java.util.List;
import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecomMembersRepository extends JpaRepository<ExecomMember,Integer>{
    List<ExecomMember> findBySocietyId(Integer societyId);

    List<ExecomMember> findByTenureEndDateIsNullOrBefore(LocalDate localDate);
    
    List<ExecomMember> findByTenureEndDateIsNotNullOrAfter(LocalDate localDate);
}
