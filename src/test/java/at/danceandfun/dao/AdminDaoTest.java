package at.danceandfun.dao;

import static org.junit.Assert.fail;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Admin;
import at.danceandfun.entity.Course;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminDaoTest {

    @Autowired
    private DaoBaseImpl<Admin> adminDao;

    @Autowired
    private DaoBaseImpl<Course> courseDao;

    public static Admin getValidAdmin() {
        Admin admin = new Admin();
        admin.setAddress(AddressDaoTest.getValidAddress());
        admin.setBirthday(new LocalDate().minusYears(10));
        admin.setFirstname("first");
        admin.setLastname("last");
        admin.setTelephone("123456789");
        admin.setEmail("mail@mail.com");
        return admin;
    }

    @Test
    public void testSave() {
        adminDao.save(getValidAdmin());
    }

    @Test
    public void testUpdate() {
        Admin admin = adminDao.getEnabledList().get(0);
        if (admin != null) {
            adminDao.update(admin);
        } else {
            fail("database is empty");
        }
    }
}
