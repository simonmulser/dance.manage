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

        criteria.add(Restrictions.or(
                Restrictions.like("firstname", query + "%"),
                Restrictions.like("lastname", query + "%")));
        
        return participantDao.getEnabledListWithCriteria(criteria);
    }

    @Override
    public List<Teacher> searchTeachers(String query) {
        DetachedCriteria criteria = DetachedCriteria
.forClass(Teacher.class);

        criteria.add(Restrictions.or(
                Restrictions.like("firstname", query + "%"),
                Restrictions.like("lastname", query + "%")));

        return teacherDao.getEnabledListWithCriteria(criteria);
    }

    public List<Parent> searchParents(String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Parent.class);

        criteria.add(Restrictions.or(
                Restrictions.like("firstname", query + "%"),
                Restrictions.like("lastname", query + "%")));

        return parentDao.getEnabledListWithCriteria(criteria);
    }

    @Override
    public List<Course> searchCourses(String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        criteria.add(Restrictions.like("name", query + "%"));

        return courseDao.getEnabledListWithCriteria(criteria);
    }

}
