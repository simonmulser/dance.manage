package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

@Service
public class SearchManagerImpl implements SearchManager {

    private static Logger logger = Logger
            .getLogger(ParticipantManagerImpl.class);

    @Autowired
    private DaoBase<Participant> participantDao;

    @Autowired
    private DaoBase<Teacher> teacherDao;

    @Autowired
    private DaoBase<Parent> parentDao;

    @Autowired
    private DaoBase<Course> courseDao;

    public List<Participant> searchParticipants(String query) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);

        String[] array = query.split(" ");
        for (int i = 0; i < array.length; i++) {
        criteria.add(Restrictions.or(
                    Restrictions.like("firstname", "%" + array[i] + "%"),
                    Restrictions.like("lastname", "%" + array[i] + "%")));
        }

        return participantDao.getEnabledListWithCriteria(criteria);
    }

    @Override
    public List<Teacher> searchTeachers(String query) {
        DetachedCriteria criteria = DetachedCriteria
.forClass(Teacher.class);

        String[] array = query.split(" ");
        for (int i = 0; i < array.length; i++) {
        criteria.add(Restrictions.or(
                    Restrictions.like("firstname", "%" + array[i] + "%"),
                    Restrictions.like("lastname", "%" + array[i] + "%")));
        }
        return teacherDao.getEnabledListWithCriteria(criteria);
    }

    public List<Parent> searchParents(String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Parent.class);

        String[] array = query.split(" ");
        for (int i = 0; i < array.length; i++) {
        criteria.add(Restrictions.or(
                    Restrictions.like("firstname", "%" + array[i] + "%"),
                    Restrictions.like("lastname", "%" + array[i] + "%")));
        }

        return parentDao.getEnabledListWithCriteria(criteria);
    }

    @Override
    public List<Course> searchCourses(String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        String[] array = query.split(" ");
        for (int i = 0; i < array.length; i++) {
            criteria.add(Restrictions.like("name", "%" + array[i] + "%"));
        }

        return courseDao.getEnabledListWithCriteria(criteria);
    }

}
