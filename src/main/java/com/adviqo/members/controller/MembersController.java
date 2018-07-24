package com.adviqo.members.controller;

import com.adviqo.members.constants.RestApi;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import com.adviqo.members.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Members API", description = "Available API for CRUD operations with members (according to the requirements")
public class MembersController {

    private final MemberService memberService;

    @Autowired
    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(RestApi.MEMBERS)
    @ApiOperation(value = "Create new member", notes = "Only first name is required field")
    @ApiResponses(@ApiResponse(code = 201, message = "Member has been created"))
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberDto memberDto) {
        return new ResponseEntity<>(memberService.save(memberDto), HttpStatus.CREATED);
    }

    @GetMapping(RestApi.MEMBERS)
    @ApiOperation(value = "Get list of all the members", notes = "Returns list of all the members")
    public List<Member> getMembers() {
        return memberService.findAll();
    }
}
