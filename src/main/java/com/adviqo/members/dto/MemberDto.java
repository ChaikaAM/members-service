package com.adviqo.members.dto;

import com.adviqo.members.model.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public final class MemberDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String postalCode;

    public final Member toMember() {
        final Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setBirthDate(birthDate);
        member.setPostalCode(postalCode);
        return member;
    }
}
