package com.adviqo.members.controller;

import com.adviqo.members.controller.api.MembersApi;
import com.adviqo.members.constants.RestApi;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import com.adviqo.members.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MembersController implements MembersApi {

    private final MemberService memberService;

    @Autowired
    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(RestApi.MEMBERS)
    @ResponseStatus(HttpStatus.CREATED)
    public Member addMember(@RequestBody MemberDto memberDto) {
        return memberService.save(memberDto);
    }

    @GetMapping(RestApi.MEMBERS + "/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @PatchMapping(RestApi.MEMBERS + "/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        return memberService.updateById(id, memberDto);
    }

    @DeleteMapping(RestApi.MEMBERS + "/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    @GetMapping(RestApi.MEMBERS)
    public List<Member> getMembers() {
        return memberService.findAll();
    }
}
