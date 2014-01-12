package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Rating;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RatingManagerTest {

    private static Logger logger = Logger.getLogger(RatingManagerImpl.class);

    @Autowired
    private CourseManager courseManager;
    @Autowired
    private RatingManager ratingManager;
    @Autowired
    private ParticipantManager participantManager;

    @SuppressWarnings("unchecked")
    private DaoBase<Rating> dao = (DaoBase<Rating>) mock(DaoBase.class);

    @SuppressWarnings("unchecked")
    @Test
    public void getEnabledRatings() {
        Participant actualParticipant = participantManager.get(1); /*
                                                                    * Franz,2
                                                                    * Feedbacks
                                                                    */
        List<Rating> enabledRatings = ratingManager
                .getEnabledRatings(actualParticipant);
        for (Rating r : enabledRatings) {
            assertThat(r.isEnabled(), is(true));
        }

    }

    @Test
    public void getNewestRatings() {
        List<Rating> newestRatings = ratingManager.getNewestRatings();
        assertThat("Highest Rid first", newestRatings.get(0).getRid(),
                greaterThan(newestRatings.get(1).getRid()));
    }
}
