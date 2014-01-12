package at.danceandfun.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Address;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

@Service
public class CourseManagerImpl extends ManagerBaseImpl<Course> implements
        CourseManager {

    private static Logger logger = Logger.getLogger(CourseManager.class);

    @Autowired
    public void setDao(DaoBaseImpl<Course> courseDao) {
        setMainDao(courseDao);
    }

    public List<Course> searchForCourses(Participant actualParticipant,
            String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        criteria.add(Restrictions.like("name", query + "%"));
        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualParticipant.getPid() == null)) {

            for (CourseParticipant cp : actualParticipant
                    .getCourseParticipants()) {
                Course actualCourse = cp.getCourse();
                Criterion rest2 = Restrictions.eq("cid", actualCourse.getCid());
                criteria.add(Restrictions.not(rest2));
            }
        }
        List<Course> courses = mainDao.getListByCriteria(criteria);
        return courses;
    }

    public List<Course> searchForCourses(Teacher actualTeacher, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        criteria.add(Restrictions.like("name", query + "%"));
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

    public List<String> getParticipantPerStyle(List<Style> enabledStyles,
            List<CourseParticipant> enabledCourseParticipants, int year) {
        List<String> participantsPerStyle = new ArrayList<String>();
        int participantCount = 0;
        for (Style style : enabledStyles) {
            DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);
            criteria.add(Restrictions.eq("enabled", true));
            criteria.add(Restrictions.eq("style.sid", style.getSid()));
            criteria.add(Restrictions.eq("year", year));
            List<Course> coursesWithStyle = mainDao.getListByCriteria(criteria);
            for (Course course : coursesWithStyle) {
                for (CourseParticipant cp : enabledCourseParticipants) {
                    if (cp.getCourse().getCid() == course.getCid()) {
                        participantCount++;
                    }
                }

            }
            participantsPerStyle.add(style.getName() + "(" + participantCount
                    + ")" + "," + participantCount);
            participantCount = 0;
        }
        return participantsPerStyle;
    }

    public List<Long> getParticipantPerLevel(int year) {
        List<Long> participantsPerLevel = mainDao
                .getQueryResultsLong("select count(*) from Course as c join c.courseParticipants as cp where c.year="
                        + year + " group by c.level");

        return participantsPerLevel;
    }

    @Override
    public List<Course> getCoursesByStudioAddress(Address address) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        criteria.add(Restrictions.eq("enabled", true));
        criteria.add(Restrictions.eq("address", address));

        List<Course> courses = mainDao.getListByCriteria(criteria);
        return courses;
    }

    @Override
    public List<Course> getEnabledCourses(Teacher teacher) {
        return mainDao
                .getQueryResults("select cou from Course as cou where cou.enabled=true and cou.teacher.pid="
                        + teacher.getPid());
    }
}
