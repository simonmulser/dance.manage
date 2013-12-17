package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import at.danceandfun.dao.DaoBase;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

public class SearchManagerImpl implements SearchManager {

    private static Logger logger = Logger
            .getLogger(ParticipantManagerImpl.class);

    @Autowired
    private DaoBase<Participant> participantDao;

    @Autowired
    private DaoBase<Teacher> teacherDao;

    @Autowired
    private DaoBase<Participant> courseDao;

    public List<Participant> searchParticipants(String query) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(Participant.class);

        criteria.add(Restrictions.or(
                Restrictions.like("firstname", query + "%"),
                Restrictions.like("lastname", query + "%")));
        
        return participantDao.getEnabledListWithCriteria(criteria);
    }

    @Override
    public List<Teacher> searchTeacher(String searchTerm) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Course> searchCourses(String query) {
        // TODO Auto-generated method stub
        return null;
    }

}
