package com.adviqo.members.api;

import com.adviqo.members.AbstractMvcTest;
import com.adviqo.members.constants.RestApi;
import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

public final class MembersApiTest extends AbstractMvcTest {

    @Test
    public final void testAddNewMemberEnpoint() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final ResponseEntity<Member> response = restTemplate.postForEntity(testServerUrl + RestApi.MEMBERS, randomMemberDto, Member.class);
        assertEquals("Creating member enpoint should return status 201 Created", HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Entity should be created with the same first name", response.getBody().getFirstName(), randomMemberDto.getFirstName());
        assertNotNull("Id should be assigned to entity", response.getBody().getId());
    }

    @Test
    public final void testAddNewMemberEnpointWithEmptyFirstName() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        randomMemberDto.setFirstName("");
        final ResponseEntity<String> response = restTemplate.postForEntity(testServerUrl + RestApi.MEMBERS, randomMemberDto, String.class);
        assertFalse("Creating member endpoint should not return 201 status in case of empty first name", HttpStatus.CREATED.equals(response.getStatusCode()));
    }

    @Test
    public final void testGetSpecificMember() {
        final ResponseEntity<Member> response = restTemplate.getForEntity(testServerUrl + RestApi.MEMBERS + "/1", Member.class);
        assertEquals("Should be found first member", response.getBody().getId(), Long.valueOf(1L));
        assertEquals("Status OK should be received", response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public final void testGetSpecificNotExistingMember() {
        final ResponseEntity<String> response = restTemplate.getForEntity(testServerUrl + RestApi.MEMBERS + "/" + Long.MAX_VALUE, String.class);
        assertFalse("Getting member endpoint should not return 200 status in case of user with unexisting id", HttpStatus.OK.equals(response.getStatusCode()));
    }

    @Test
    public final void testUpdateMember() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final ResponseEntity<Member> response = restTemplate.exchange(testServerUrl + RestApi.MEMBERS + "/1", HttpMethod.PATCH, new HttpEntity<>(randomMemberDto), Member.class);
        assertEquals("Updating member endpoint should return status 200", HttpStatus.OK, response.getStatusCode());
        assertEquals("Birthday of member should be modified", randomMemberDto.getBirthDate(), response.getBody().getBirthDate());
    }

    @Test
    public final void testUpdateNotExistingMember() {
        final MemberDto randomMemberDto = createRandomMemberDto();
        final ResponseEntity<String> response = restTemplate.exchange(testServerUrl + RestApi.MEMBERS + "/" + Long.MAX_VALUE, HttpMethod.PATCH, new HttpEntity<>(randomMemberDto), String.class);
        assertFalse("Updating member endpoint should not return 200 status in case of user with unexisting id", HttpStatus.OK.equals(response.getStatusCode()));
    }

    @Test
    public final void testGetMembersEnpoint() {
        final ResponseEntity<List> response = restTemplate.getForEntity(testServerUrl + RestApi.MEMBERS, List.class);
        assertEquals("Getting members endpoint should return status 200", HttpStatus.OK, response.getStatusCode());
        assertFalse("Getting members endpoint should return not empty collection of members", response.getBody().isEmpty());
    }

    @Test
    public final void testDeleteUserMemberEndpoint() {
        final Member testMember = restTemplate.getForObject(testServerUrl + RestApi.MEMBERS + "/2", Member.class);
        final ResponseEntity<String> response = restTemplate.exchange(testServerUrl + RestApi.MEMBERS + "/" + testMember.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertEquals("Deleting members endpoint should return status 200 if request was successful", HttpStatus.OK, response.getStatusCode());

        final List<Member> allMembers = restTemplate.getForObject(testServerUrl + RestApi.MEMBERS, List.class);
        assertFalse("Member should be successfully removed from the members table", allMembers.contains(testMember));
    }
}
