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
import at.danceandfun.entity.SuperUser;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SuperUserDaoTest {

    @Autowired
    private DaoBaseImpl<SuperUser> superUserDao;

    @Autowired
    private DaoBaseImpl<Course> courseDao;

    public static SuperUser getValidSuperUser() {
        SuperUser superUser = new SuperUser();
        superUser.setAddress(AddressDaoTest.getValidAddress());
        superUser.setBirthday(new LocalDate().minusYears(10));
        superUser.setFirstname("first");
        superUser.setLastname("last");
        superUser.setTelephone("123456789");
        superUser.setEmail("mail@mail.com");
        return superUser;
    }

    @Test
    public void testSave() {
        superUserDao.save(getValidSuperUser());
    }

    @Test
    public void testUpdate() {
        SuperUser superUser = superUserDao.getEnabledList().get(0);
        if (superUser != null) {
            superUserDao.update(superUser);
        } else {
            fail("database is empty");
        }
    }
}
