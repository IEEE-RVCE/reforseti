package org.ieeervce.api.siterearnouveau.service;

import java.util.List;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.repository.ExecomMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public List<ExecomMember> findCurrentBySocietyId(int societyId) {
        return this.execomMembersRepository.findByTenureEndDateIsNullAndSocietyId(societyId);
    }

    public List<ExecomMember> findAlumniBySocietyId(int societyId) {
        return this.execomMembersRepository.findByTenureEndDateIsNotNullAndSocietyId(societyId);
    }

    public ExecomMember create(ExecomMember execomMember) {
        return this.execomMembersRepository.save(execomMember);
    }

    public ExecomMember updateExecomMember(int execomMemberId, ExecomMember execomMemberUpdater) {
        ExecomMember execomMember = this.execomMembersRepository.getReferenceById(execomMemberId);
        execomMember.setFirstName(execomMemberUpdater.getFirstName());
        execomMember.setLastName(execomMemberUpdater.getLastName());
        execomMember.setImagePath(execomMemberUpdater.getImagePath());
        execomMember.setPosition(execomMemberUpdater.getPosition());
        execomMember.setTenureStartDate(execomMemberUpdater.getTenureStartDate());
        execomMember.setTenureEndDate(execomMemberUpdater.getTenureEndDate());
        return this.execomMembersRepository.save(execomMember);
    }

    public void deleteByMemberId(int id) {
        this.execomMembersRepository.deleteById(id);
    }

}
