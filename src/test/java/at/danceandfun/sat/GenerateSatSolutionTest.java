package at.danceandfun.sat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Performance;
import at.danceandfun.exception.SatException;
import at.danceandfun.service.CourseManager;
import at.danceandfun.service.ParticipantManager;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenerateSatSolutionTest {

    private static Logger logger = Logger
            .getLogger(GenerateSatSolutionTest.class);

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private ParticipantManager participantManager;

    @Test
    public void testIf3PerformancesAreCreated() {
        GenerateSatSolution solution = new GenerateSatSolution();
        try {
            Map<Integer, Performance> performanceMap = solution
                    .generatePerformance(courseManager.getEnabledList(),
                            participantManager.getEnabledList(), true, true,
                            true, true, true, false, false);
            assertTrue(performanceMap.size() == 3);
        } catch (IOException e) {

        } catch (SatException e) {

        }
    }

    @Test
    public void testGenerateSolution() {
        GenerateSatSolution solution = new GenerateSatSolution();
        try {
            Map<Integer, Performance> performanceMap = solution
                    .generatePerformance(courseManager.getEnabledList(),
                            participantManager.getEnabledList(), true, true,
                            true, true, true, false, false);
            assertThat(performanceMap.get(1), is(notNullValue()));
            assertThat(performanceMap.get(2), is(notNullValue()));
            assertThat(performanceMap.get(3), is(notNullValue()));
        } catch (IOException e) {

        } catch (SatException e) {

        }
    }

    @Test
    public void testNoSelectedRestrictions() {
        GenerateSatSolution solution = new GenerateSatSolution();
        try {
            Map<Integer, Performance> performanceMap = solution
                    .generatePerformance(courseManager.getEnabledList(),
                            participantManager.getEnabledList(), false, false,
                            false, false, false, false, false);
            assertThat(performanceMap.get(1), is(notNullValue()));
            assertThat(performanceMap.get(2), is(notNullValue()));
            assertThat(performanceMap.get(3), is(notNullValue()));
        } catch (IOException e) {

        } catch (SatException e) {

        }
    }

}
