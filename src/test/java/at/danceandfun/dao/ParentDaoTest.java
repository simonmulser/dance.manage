package at.danceandfun.dao;

import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Parent;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParentDaoTest {

    @Autowired
    private DaoBaseImpl<Parent> parentDao;

    @Autowired
    private DaoBaseImpl<Course> courseDao;

    public static Parent getValidParent() {
        Parent parent = new Parent();
        parent.setAddress(AddressDaoTest.getValidAddress());
        parent.setBirthday(new LocalDate().minusYears(10));
        parent.setFirstname("first");
        parent.setLastname("last");
        parent.setTelephone("123456789");
        parent.setEmail("mail@mail.com");
        return parent;
    }

    @Test
    public void testSave() {
        parentDao.persist(getValidParent());
    }

    @Test
    public void testUpdate() {
        Parent parent = parentDao.getEnabledList().get(0);
        if (parent != null) {
            parentDao.merge(parent);
        } else {
            fail("database is empty");
        }
    }
}
