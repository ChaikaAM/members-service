package com.adviqo.members.service;

import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.exception.IllegalMemberDtoException;
import com.adviqo.members.exception.MemberNotFoundException;
import com.adviqo.members.model.Member;
import com.adviqo.members.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public final Member save(@Valid MemberDto memberDto) {
        log.info("Saving member {}", memberDto);
        if (memberDto.getFirstName() == null || memberDto.getFirstName().isEmpty()) {
            log.error("Member could not be saved with null or empty firstName, bad data - {}", memberDto);
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
        log.info("Updating member {} by data {}", existingMember, memberDto);
        if (memberDto.getFirstName() != null) {
            if (memberDto.getFirstName().isEmpty()) {
                log.error("Member could not be updated with empty firstName, bad data - {}", memberDto);
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
            log.info("Member with id {} has been deleted", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Could not delete member with id {}. Not found", id);
            throw new MemberNotFoundException("Member could not be deleted because id has not been found in database");
        }
    }

    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }
}
