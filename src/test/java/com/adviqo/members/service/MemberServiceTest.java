package com.adviqo.members.service;

import com.adviqo.members.AbstractMvcTest;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.exception.IllegalMemberDtoException;
import com.adviqo.members.exception.MemberNotFoundException;
import com.adviqo.members.model.Member;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class MemberServiceTest extends AbstractMvcTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testSave() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final Member savedMember = memberService.save(randomMemberDto);
        assertEquals("Member should be successfully saved with the same birthdate as in request", randomMemberDto.getBirthDate(), savedMember.getBirthDate());
    }

    @Test(expected = IllegalMemberDtoException.class)
    public void testSaveWithoutFirstName() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        randomMemberDto.setFirstName("");
        memberService.save(randomMemberDto);
    }

    @Test
    public void testFindAll() {
        final List<Member> foundedMembers = memberService.findAll();
        assertFalse("Members should be found", foundedMembers.isEmpty());
    }

    @Test
    public void testFindById() {
        final Member foundedMember = memberService.findById(1L);
        assertEquals("Correct member should be founded", foundedMember.getId(), Long.valueOf(1L));
    }

    @Test(expected = MemberNotFoundException.class)
    public void testFindByUnexistingId() {
        memberService.findById(Long.MAX_VALUE);
    }

    @Test
    public void testUpdateById() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final Member savedMemberBeforeUpdate = memberService.save(randomMemberDto);
        randomMemberDto.setFirstName(randomMemberDto.getFirstName() + "delta");
        final Member savedMemberAfterUpdate = memberService.updateById(savedMemberBeforeUpdate.getId(), randomMemberDto);
        assertNotEquals("First name should be changed", savedMemberBeforeUpdate.getFirstName(), savedMemberAfterUpdate.getFirstName());
        assertEquals("First name should be changed by delta", savedMemberBeforeUpdate.getFirstName() + "delta", savedMemberAfterUpdate.getFirstName());
    }

    @Test
    public void testDeleteMember() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final Member savedMember = memberService.save(randomMemberDto);
        memberService.deleteMember(savedMember.getId());
        assertFalse("Member should be deleted", memberService.existsById(savedMember.getId()));
    }
}