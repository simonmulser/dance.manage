package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Position;

@Service
public class CourseParticipantManagerImpl extends
        ManagerBaseImpl<CourseParticipant> implements CourseParticipantManager {

    private static Logger logger = Logger
            .getLogger(CourseParticipantManagerImpl.class);

    @Autowired
    public void setDao(DaoBaseImpl<CourseParticipant> courseParticipantDao) {
        setMainDao(courseParticipantDao);
    }

    public int getCourseCountByParticipant(int cid, int pid) {
        return mainDao
                .getQueryResultsCount("select count(*) from CourseParticipant as cp where cp.enabled=true and cp.course.cid = "
                        + cid + " and cp.participant.pid=" + pid);
    }

    @Override
    public CourseParticipant getCourseParticipantByPosition(Position position) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(CourseParticipant.class);
        logger.debug("PID:"
                + position.getKey().getInvoice().getParticipant().getPid());
        logger.debug("CID:"
                + position.getKey().getInvoice().getParticipant().getPid());
        logger.debug("DURATION:" + position.getDuration().getValue());
        criteria.add(Restrictions.eq("participant.pid", position.getKey()
                .getInvoice().getParticipant().getPid()));
        criteria.add(Restrictions.eq("course.cid", position.getKey()
                .getCourse().getCid()));
        criteria.add(Restrictions.eq("duration", position.getDuration()
                .getValue()));
        criteria.add(Restrictions.eq("enabled", true));
        List<CourseParticipant> courseParticipants = mainDao
                .getListByCriteria(criteria);
        return courseParticipants.get(0);
    }

}
