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

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractMvcTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String testServerUrl;

    @PostConstruct
    public void setUp() {
        testServerUrl = "http://localhost:" + port;
    }

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
        memberDto.setBirthDate(LocalDate.of(1986, 6, 13));
        memberDto.setPostalCode(RandomStringUtils.random(5, true, true));
        return memberDto;
    }

    private Member createRandomMember() {
        return createRandomMemberDto().toMember();
    }
}
