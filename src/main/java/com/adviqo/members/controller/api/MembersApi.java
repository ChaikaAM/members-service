package com.adviqo.members.controller.api;

import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Members API", description = "Available API for CRUD operations with members (according to the requirements)")
public interface MembersApi {

    @ApiOperation(value = "Create new member", notes = "Only first name is required field")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Member has been created"),
            @ApiResponse(code = 400, message = "At lease first name should not be empty")
    })
    Member addMember(@RequestBody MemberDto memberDto);

    @ApiOperation(value = "Get specific member by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success request"),
            @ApiResponse(code = 404, message = "Member with requested id has not been found")
    })
    Member getMember(@PathVariable Long id);

    @ApiOperation(value = "Update member", notes = "Updates existing member by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Member has been updated"),
            @ApiResponse(code = 400, message = "Request should not contain empty first name parameter"),
            @ApiResponse(code = 404, message = "Member with requested id has not been found")
    })
    Member updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto);

    @ApiOperation(value = "Delete member", notes = "Deletes existing member by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Member has been deleted"),
            @ApiResponse(code = 404, message = "Member with requested id has not been found")
    })
    void deleteMember(@PathVariable Long id);

    @ApiOperation(value = "Get list of all the members", notes = "Returns list of all the members")
    List<Member> getMembers();
}
