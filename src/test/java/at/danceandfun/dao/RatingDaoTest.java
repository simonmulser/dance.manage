package at.danceandfun.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Rating;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RatingDaoTest {

    @Autowired
    private DaoBaseImpl<Rating> ratingDao;

    public static Rating getValidRating() {
        Rating rating = new Rating();
        rating.setParticipant(ParticipantDaoTest.getValidParticipant());
        rating.setCourse(CourseDaoTest.getValidCourse());
        rating.setEnabled(true);
        return rating;
    }

    @Test
    public void testSave() {
        ratingDao.persist(getValidRating());
    }

    @Test
    public void testUpdate() {
        Rating rating = ratingDao.get(1);
        if (rating != null) {
            ratingDao.merge(rating);
        } else {
            fail("database is empty");
        }
    }
}
