package com.adviqo.members.controller;

import com.adviqo.members.AbstractMvcTest;
import com.adviqo.members.constants.RestApi;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public final class MembersControllerTest extends AbstractMvcTest {

    @Test
    public final void testGetMembersEnpoint() {
        final ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + RestApi.MEMBERS, List.class);
        assertEquals("Getting members endpoint should return status 200", HttpStatus.OK, response.getStatusCode());
        assertFalse("Getting members endpoint should return not empty collection of members", response.getBody().isEmpty());
    }

    @Test
    public final void testAddNewMemberEnpoint() {
        MemberDto randomMemberDto = createRandomMemberDto();
        ResponseEntity<Member> response = restTemplate.postForEntity("http://localhost:" + port + RestApi.MEMBERS, randomMemberDto, Member.class);
        assertEquals("Creating member enpoint should retuen status 201 Created", HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Entity should be created with the same first name", response.getBody().getFirstName(), randomMemberDto.getFirstName());
        assertNotNull("Id should be assigned to entity", response.getBody().getId());
    }
}
