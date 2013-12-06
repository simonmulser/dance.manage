package at.danceandfun.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeacherDaoTest {

    @Autowired
    private DaoBaseImpl<Teacher> teacherDao;
    @Autowired
    private DaoBaseImpl<Course> courseDao;
    @Autowired
    private DaoBaseImpl<Style> styleDao;

    public static Teacher getValidTeacher() {
        Teacher teacher = new Teacher();
        teacher.setAddress(AddressDaoTest.getValidAddress());
        teacher.setBirthday(new LocalDate().minusYears(10));
        teacher.setFirstname("first");
        teacher.setLastname("last");
        teacher.setTelephone("123456789");
        teacher.setEmail("mail@mail.com");
        teacher.setSalary(400.50);
        teacher.setSvnr("1020120690");
        return teacher;
    }

    @Test
    public void testSave() {
        teacherDao.save(getValidTeacher());
    }

    @Test
    public void testUpdate() {
        Teacher teacher = teacherDao.get(7);
        if (teacher != null) {
            teacherDao.update(teacher);
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testGetStyles() {
        Teacher teacher = teacherDao.get(7);
        if (teacher != null) {
            assertThat(teacher.getStyles().size(), is(1));
        } else {
            fail("database is empty");
        }
    }

    @Test
    public void testListByCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Teacher.class);
        assertThat(teacherDao.getListByCriteria(criteria).isEmpty(), is(false));
    }

    @Test
    public void testListByCriteriaWithOffsetAndSize() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Teacher.class);
        assertThat(teacherDao.getListByCriteria(criteria, 0, 1).isEmpty(),
                is(false));
    }

    @Test
    public void testListByCriterions() {
        List<Criterion> criterions = new ArrayList<Criterion>();

        criterions.add(Restrictions.eq("enabled", true));
        assertThat(teacherDao.getListByCriterions(criterions).isEmpty(),
                is(false));
    }

    @Test
    public void testTeacherCourseRelation() {
        Course course = CourseDaoTest.getValidCourse();
        courseDao.update(course);

        Teacher teacher = getValidTeacher();
        course.setTeacher(teacher);

        List<Course> courses = new ArrayList<Course>();
        courses.add(course);
        teacher.setCourses(courses);

        teacherDao.update(teacher);
    }

    @Test
    public void overrideEquals() {
        Teacher newTeacher = new Teacher();
        newTeacher.setPid(2);
        Teacher oldTeacher = teacherDao.get(2);
        List<Teacher> teachers = new ArrayList<Teacher>();
        teachers.add(oldTeacher);
        assertThat(teachers.contains(newTeacher), is(true));
    }
}
