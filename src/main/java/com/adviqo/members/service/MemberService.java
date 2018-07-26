package com.adviqo.members.service;

import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.exception.IllegalMemberDtoException;
import com.adviqo.members.exception.MemberNotFoundException;
import com.adviqo.members.model.Member;
import com.adviqo.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public final Member save(@Valid MemberDto memberDto) {
        if (memberDto.getFirstName() == null || memberDto.getFirstName().isEmpty()) {
            throw new IllegalMemberDtoException("Member should have at least first name parameter");
        }
        return memberRepository.save(memberDto.toMember());
    }

    public final List<Member> findAll() {
        return memberRepository.findAll();
    }

    public final Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("No member found with id " + id));
    }

    public final Member updateById(Long id, MemberDto memberDto) {
        final Member existingMember = findById(id);
        if (memberDto.getFirstName() != null) {
            if (memberDto.getFirstName().isEmpty()) {
                throw new IllegalMemberDtoException("Member could not be updated with empty first name parameter");
            }
            existingMember.setFirstName(memberDto.getFirstName());
        }
        if (memberDto.getLastName() != null) {
            existingMember.setLastName(memberDto.getLastName());
        }
        if (memberDto.getBirthDate() != null) {
            existingMember.setBirthDate(memberDto.getBirthDate());
        }
        if (memberDto.getPostalCode() != null) {
            existingMember.setPostalCode(memberDto.getPostalCode());
        }
        return memberRepository.save(existingMember);
    }

    public final void deleteMember(Long id) {
        try {
            memberRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("Member could not be deleted because id has not been found in database");
        }
    }

    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }
}
