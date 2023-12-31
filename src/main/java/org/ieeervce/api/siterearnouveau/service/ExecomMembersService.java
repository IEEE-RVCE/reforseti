package org.ieeervce.api.siterearnouveau.service;

import java.time.LocalDate;
import java.util.List;

import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.repository.ExecomMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecomMembersService {
    private ExecomMembersRepository execomMembersRepository;

    @Autowired
    public ExecomMembersService(ExecomMembersRepository execomMembersRepository) {
        this.execomMembersRepository = execomMembersRepository;
    }

    public List<ExecomMember> findAll() {
        return this.execomMembersRepository.findAll();
    }

    public List<ExecomMember> findBySocietyId(int societyId) {
        return this.execomMembersRepository.findBySocietyId(societyId);
    }
    public List<ExecomMember> findCurrentBySocietyId(int societyId){
        return this.execomMembersRepository.findByTenureEndDateIsNullAndSocietyId(societyId);
    }

    public void deleteByMemberId(int id){
        this.execomMembersRepository.deleteById(id);
    }
}
