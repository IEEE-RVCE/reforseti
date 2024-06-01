package org.ieeervce.api.siterearnouveau.service;

import io.micrometer.core.annotation.Timed;
import org.ieeervce.api.siterearnouveau.entity.Society;
import org.ieeervce.api.siterearnouveau.repository.SocietiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Timed
public class SocietyService {
    private final SocietiesRepository societiesRepository;
    public SocietyService(SocietiesRepository societiesRepository) {
        this.societiesRepository = societiesRepository;
    }

    public List<Society> list(){
        return societiesRepository.findAll();
    }

    public Optional<Society> getByActualSocietyId(short societyId){
        return societiesRepository.findByActualSocietyId(societyId);
    }

    public Society createOrUpdate(Society society){
        return societiesRepository.save(society);
    }

    public void deleteSociety(short societyId){
        societiesRepository.deleteByActualSocietyId(societyId);
    }
}
