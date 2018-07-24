package com.adviqo.members;

import com.adviqo.members.dto.MemberDto;
import com.adviqo.members.model.Member;
import com.adviqo.members.repository.MemberRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractMvcTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void prepareTestEnvironment() {
        for (int i = 0; i < 10; i++) {
            memberRepository.save(createRandomMember());
        }
    }

    protected MemberDto createRandomMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setFirstName("User" + RandomStringUtils.random(5, true, true));
        memberDto.setLastName(RandomStringUtils.random(5, true, true));
        memberDto.setBirthDate(LocalDateTime.of(1986, 6, 13, 12, 00));
        memberDto.setPostalCode(RandomStringUtils.random(5, true, true));
        return memberDto;
    }

    private Member createRandomMember() {
        return createRandomMemberDto().toMember();
    }
}
