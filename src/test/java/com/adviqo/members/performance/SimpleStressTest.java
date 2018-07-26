package com.adviqo.members.performance;

import com.adviqo.members.AbstractMvcTest;
import com.adviqo.members.constants.RestApi;
import com.adviqo.members.model.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Log4j2
@RunWith(Parameterized.class)
public class SimpleStressTest extends AbstractMvcTest {

    private final int testTimeSecs;
    private volatile boolean isTimerOn;
    private volatile int requestsCounter = 0;

    public SimpleStressTest(int testTimeSecs) {
        this.testTimeSecs = testTimeSecs;
    }

    @Parameters
    public static Collection stressTimings() {
        return Arrays.asList(1, 3, 5, 10);
    }

    @Before
    public void scheduleTimerStopping() {
        isTimerOn = true;
        Executors.newSingleThreadScheduledExecutor().schedule(() -> isTimerOn = false, testTimeSecs, TimeUnit.SECONDS);
    }

    @Test
    public void testPerformance() {
        int createdMembersBefore = restTemplate.getForEntity(testServerUrl + RestApi.MEMBERS, List.class).getBody().size();
        while (isTimerOn) {
            restTemplate.postForEntity(testServerUrl + RestApi.MEMBERS, createRandomMemberDto(), Member.class);
            requestsCounter++;
        }
        int createdMembers = restTemplate.getForEntity(testServerUrl + RestApi.MEMBERS, List.class).getBody().size() - createdMembersBefore;
        log.info("App was under the stress for the {} secs", testTimeSecs);
        log.info("During test there are {} requests have been handled and {} members have been created", requestsCounter, createdMembers);
        log.info("Average results of {} secs stress: {} requests per secs, {} members created per sec", testTimeSecs, requestsCounter / testTimeSecs, createdMembers / testTimeSecs);
    }


    /**
     * The staff below is used for using together both test runners (to add context)
     */
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
}
