package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

@Service
public class CourseManagerImpl extends ManagerBaseImpl<Course> implements
        CourseManager {

    @Autowired
    public void setDao(DaoBaseImpl<Course> courseDao) {
        setMainDao(courseDao);
    }

    public List searchForCourses(Participant actualParticipant, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        Criterion rest1 = Restrictions.like("name", query + "%");
        criteria.add(rest1);

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualParticipant.getPid() == null)) {

            for (CourseParticipant cp : actualParticipant
                    .getCourseParticipants()) {
                Course actualCourse = cp.getKey().getCourse();
                Criterion rest2 = Restrictions.eq("cid", actualCourse.getCid());
                criteria.add(Restrictions.not(rest2));
            }
        }
        List<Course> courses = mainDao.getListByCriteria(criteria);
        return courses;
    }

    public List searchForCourses(Teacher actualTeacher, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        Criterion rest1 = Restrictions.like("name", query + "%");
        criteria.add(rest1);

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualTeacher.getPid() == null)) {
            for (Course c : actualTeacher.getCourses()) {
                Criterion rest2 = Restrictions.eq("cid", c.getCid());
                criteria.add(Restrictions.not(rest2));
            }
        }
        List<Course> courses = mainDao.getListByCriteria(criteria);
        return courses;
    }

}
