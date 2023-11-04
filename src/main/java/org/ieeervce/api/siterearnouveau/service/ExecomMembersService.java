package org.ieeervce.api.siterearnouveau.service;

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

    public List<ExecomMember> list(){
        return this.execomMembersRepository.findAll();
    }
}
