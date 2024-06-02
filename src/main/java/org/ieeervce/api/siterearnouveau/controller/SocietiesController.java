package org.ieeervce.api.siterearnouveau.controller;

import io.micrometer.core.annotation.Timed;
import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.society.SocietyDTO;
import org.ieeervce.api.siterearnouveau.entity.Society;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.service.SocietyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/society")
@Timed
public class SocietiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocietiesController.class);
    private final SocietyService societyService;
    private final ModelMapper modelMapper;

    public SocietiesController(SocietyService societyService, ModelMapper modelMapper) {
        this.societyService = societyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResultsDTO<List<Society>> list() {
        return new ResultsDTO<>(societyService.list());
    }

    @GetMapping("/{sid}")
    public ResultsDTO<Society> getBySocietyId(@PathVariable("sid") short actualSocietyId) throws DataNotFoundException {
        Society society = societyService.getByActualSocietyId(actualSocietyId)
                .orElseThrow(DataNotFoundException::new);
        return new ResultsDTO<>(society);
    }

    @PostMapping()
    public ResultsDTO<Society> create(@RequestBody SocietyDTO societyDTO){
        Society society = modelMapper.map(societyDTO, Society.class);
        Society createdSociety = societyService.createOrUpdate(society);
        LOGGER.info("Created new society={}",createdSociety);
        return new ResultsDTO<>(createdSociety);
    }
    @PutMapping("/{sid}")
    public ResultsDTO<Society> update(@PathVariable("sid") short societyId,@RequestBody SocietyDTO societyDTO) throws DataNotFoundException {
        Society society = societyService.getByActualSocietyId(societyId).orElseThrow(DataNotFoundException::new);
        modelMapper.map(societyDTO,society);
        Society updatedSociety = societyService.createOrUpdate(society);
        return new ResultsDTO<>(updatedSociety);
    }

    @DeleteMapping("/{sid}")
    public ResultsDTO<Void> delete(@PathVariable("sid") short societyId){
        societyService.deleteSociety(societyId);
        return new ResultsDTO<>(null);
    }
}
