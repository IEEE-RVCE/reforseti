package org.ieeervce.api.siterearnouveau.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.ieeervce.api.siterearnouveau.entity.ExecomMember;
import org.ieeervce.api.siterearnouveau.repository.ExecomMembersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExecomMembersServiceTest {
    private static final int SOCIETY_ID = 1;
    @Mock
    ExecomMembersRepository execomMembersRepository;
    @InjectMocks
    ExecomMembersService execomMembersService;

    @Mock
    ExecomMember member1;
    @Mock
    ExecomMember member2;

    @Test
    void testFindAll() {
        List<ExecomMember> memberList = Arrays.asList(member1, member2);
        when(execomMembersRepository.findAll()).thenReturn(memberList);

        List<ExecomMember> returnedMemberList = execomMembersService.findAll();
        assertSame(memberList, returnedMemberList);
    }

    @Test
    void testFindCurrentBySocietyId() {
        List<ExecomMember> memberList = Arrays.asList(member1, member2);
        when(execomMembersRepository.findByTenureEndDateIsNullAndSocietyId(SOCIETY_ID)).thenReturn(memberList);

        List<ExecomMember> returnedMemberList = execomMembersService.findCurrentBySocietyId(SOCIETY_ID);
        assertSame(memberList, returnedMemberList);
    }

    @Test
    void testFindAlumniBySocietyId() {
        List<ExecomMember> memberList = Arrays.asList(member1, member2);
        when(execomMembersRepository.findByTenureEndDateIsNotNullAndSocietyId(SOCIETY_ID)).thenReturn(memberList);

        List<ExecomMember> returnedMemberList = execomMembersService.findAlumniBySocietyId(SOCIETY_ID);
        assertSame(memberList, returnedMemberList);
    }

    @Test
    void testCreate() {
        when(execomMembersRepository.save(member1)).thenReturn(member2);

        ExecomMember returnedExecomMember = execomMembersService.create(member1);
        assertSame(member2, returnedExecomMember);
    }

    @Test
    void testDeleteByMemberId() {
        int memberId = 1;
        assertDoesNotThrow(() -> {
            execomMembersService.deleteByMemberId(memberId);
        });
        verify(execomMembersRepository).deleteById(memberId);
    }

    @Test
    void testUpdate() {
        int memberId = 1;
        when(execomMembersRepository.getReferenceById(memberId)).thenReturn(member2);

        ExecomMember savedMember = mock(ExecomMember.class);
        when(execomMembersRepository.save(member2)).thenReturn(savedMember);
        ExecomMember returnedMember = execomMembersService.updateExecomMember(memberId, member1);
        assertSame(savedMember,returnedMember);
        
        verify(member1).getFirstName();
        verify(member1).getLastName();
        verify(member1).getImagePath();
        verify(member1).getPosition();
        verify(member1).getTenureStartDate();
        verify(member1).getTenureEndDate();
        verify(member2).setFirstName(any());
        verify(member2).setLastName(any());
        verify(member2).setImagePath(any());
        verify(member2).setPosition(any());
        verify(member2).setTenureStartDate(any());
        verify(member2).setTenureEndDate(any());
        
    }
}
