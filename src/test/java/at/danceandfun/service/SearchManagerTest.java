package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Participant;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchManagerTest {
    private static Logger logger = Logger.getLogger(SearchManagerTest.class);

    @Autowired
    private SearchManager searchManager;

    @Test
    public void searchParticipantsWithQuery() {
        List<Participant> p = searchManager.searchParticipants("M");
        assertThat(p, is(notNullValue()));
    }

    @Test
    public void searchTeachersWithQuery() {
        assertThat(searchManager.searchTeachers("Bo"), is(notNullValue()));
    }

    @Test
    public void searchParentsWithQuery() {
        assertThat(searchManager.searchTeachers(""), is(notNullValue()));
    }

    @Test
    public void searchCoursesWithQuery() {
        assertThat(searchManager.searchCourses("Ballett"),
                is(notNullValue()));
    }
}
