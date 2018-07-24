package com.adviqo.members.service;

import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import com.adviqo.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Member save(@Valid MemberDto memberDto) {
        return memberRepository.save(memberDto.toMember());
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
