package org.ieeervce.api.siterearnouveau.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.execom.ExecomMemberDTO;
import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.service.ExecomMembersService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/execom")
public class ExecomMembersController {

    private ExecomMembersService execomMembersService;
    private ModelMapper modelMapper;

    public ExecomMembersController(ExecomMembersService execomMembersService, ModelMapper modelMapper) {
        this.execomMembersService = execomMembersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResultsDTO<List<ExecomMember>> findAll() {
        List<ExecomMember> members = execomMembersService.findAll();
        return new ResultsDTO<>(members);
    }

    @GetMapping("/{societyId}")
    public ResultsDTO<List<ExecomMember>> listCurrentBySocietyId(@PathVariable int societyId) {
        List<ExecomMember> members = execomMembersService.findCurrentBySocietyId(societyId);
        return new ResultsDTO<>(members);
    }

    @GetMapping("/alumni/{societyId}")
    public ResultsDTO<Map<Integer, List<ExecomMemberDTO>>> listAlumniBySocietyId(@PathVariable int societyId) {
        List<ExecomMember> members = execomMembersService.findAlumniBySocietyId(societyId);
        return new ResultsDTO<>(getAlumniDTO(members));
    }

    @DeleteMapping("/{execomId}")
    public ResultsDTO<Void> deleteMember(@PathVariable int execomId) {
        execomMembersService.deleteByMemberId(execomId);
        return new ResultsDTO<>(null);
    }

    @PutMapping("/{execomId}")
    public ResultsDTO<ExecomMember> updateMember(@PathVariable int execomId,@RequestBody ExecomMemberDTO execomMemberDTO) {
        ExecomMember execomMember = modelMapper.map(execomMemberDTO, ExecomMember.class);
        return new ResultsDTO<>(execomMembersService.updateExecomMember(execomId, execomMember));
    }

    @PostMapping("")
    public ResultsDTO<ExecomMember> createMember(@RequestBody ExecomMemberDTO execomMemberDTO){
        ExecomMember newExecomMember = modelMapper.map(execomMemberDTO,ExecomMember.class);
        return new ResultsDTO<>(execomMembersService.create(newExecomMember));
    }



    private Map<Integer, List<ExecomMemberDTO>> getAlumniDTO(List<ExecomMember> members) {
        Map<Integer, List<ExecomMemberDTO>> dtoMap = members.stream()
                .map(e -> modelMapper.map(e, ExecomMemberDTO.class))
                .collect(Collectors.toMap(
                        this::getYearFromExecomMember,
                        this::getExecomMemberListSingleton, 
                        this::mergExecomMemberDTOLists));
        return (dtoMap);
    }

    private int getYearFromExecomMember(ExecomMemberDTO dto) {
        LocalDate tenureStartDate = Optional.of(dto.getTenureEndDate()).orElseThrow(
                () -> new IllegalStateException("Tenure start date missing for execom member"));
        return tenureStartDate.getYear();
    }

    private List<ExecomMemberDTO> getExecomMemberListSingleton(ExecomMemberDTO dto) {
        ArrayList<ExecomMemberDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        return dtoList;
    }
    private List<ExecomMemberDTO> mergExecomMemberDTOLists(List<ExecomMemberDTO> list1, List<ExecomMemberDTO> list2) {
        list1.addAll(list2);
        return list1;
    }
}
