package org.ieeervce.api.siterearnouveau.repository;

import java.util.List;
import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecomMembersRepository extends JpaRepository<ExecomMember,Integer>{
    List<ExecomMember> findBySocietyId(Integer societyId);

    List<ExecomMember> findByTenureEndDateIsNotNullAndSocietyId(int societyId);
    
    List<ExecomMember> findByTenureEndDateIsNullAndSocietyId(int societyId);
}
