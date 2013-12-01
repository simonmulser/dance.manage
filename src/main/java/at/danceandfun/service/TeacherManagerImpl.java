package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Teacher;

@Service
public class TeacherManagerImpl extends ManagerBaseImpl<Teacher> implements
        TeacherManager {

    @Autowired
    public void setDao(DaoBaseImpl<Teacher> teacherDao) {
        setMainDao(teacherDao);
    }

    public List searchForTeachers(Course actualCourse, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Teacher.class);

        Criterion rest1 = Restrictions.like("firstname", query + "%");
        Criterion rest2 = Restrictions.like("lastname", query + "%");
        criteria.add(Restrictions.or(rest1, rest2));

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualCourse.getCid() == null)
                && !(actualCourse.getTeacher().getPid() == null)) {
            Criterion rest3 = Restrictions.eq("pid", actualCourse.getTeacher()
                    .getPid());
            criteria.add(Restrictions.not(rest3));
        }
        List<Teacher> teachers = mainDao.getListByCriteria(criteria);
        return teachers;
    }

}
