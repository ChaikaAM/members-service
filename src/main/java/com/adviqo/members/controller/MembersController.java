package com.adviqo.members.controller;

import com.adviqo.members.constants.RestApi;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import com.adviqo.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MembersController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(RestApi.MEMBERS)
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberDto memberDto) {
        return new ResponseEntity<>(memberRepository.save(memberDto.toMember()), HttpStatus.CREATED);
    }

    @GetMapping(RestApi.MEMBERS)
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }
}
