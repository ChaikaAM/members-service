package com.adviqo.members.dto;

import com.adviqo.members.model.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public final class MemberDto {

    @NotEmpty
    private String firstName;

    private String lastName;
    private LocalDateTime birthDate;
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
