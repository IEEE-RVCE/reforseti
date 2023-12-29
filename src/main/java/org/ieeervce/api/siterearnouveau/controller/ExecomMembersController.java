package org.ieeervce.api.siterearnouveau.controller;

import java.util.List;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.service.ExecomMembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/execom")
public class ExecomMembersController {

    private ExecomMembersService execomMembersService;

    public ExecomMembersController(ExecomMembersService execomMembersService) {
        this.execomMembersService = execomMembersService;
    }

    @GetMapping
    public ResultsDTO<List<ExecomMember>> list(){
        List<ExecomMember> members = execomMembersService.findAll();
        return new ResultsDTO<>(members);
    }

    @GetMapping("/{societyId}")
    public ResultsDTO<List<ExecomMember>> listBySocietyId(@PathVariable int societyId){
        List<ExecomMember> members = execomMembersService.findBySocietyId(societyId);
        return new ResultsDTO<>(members);
    }
}
