package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Position;

@Service
public class CourseParticipantManagerImpl extends
        ManagerBaseImpl<CourseParticipant> implements CourseParticipantManager {

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
    public List<CourseParticipant> getCourseParticipantsByCount() {
        return mainDao
                .getQueryResults("select cp from CourseParticipant as cp where cp.enabled=true "
                        + "group by cp.participant.pid order by count(distinct cp.course.cid) desc");
    }

    @Override
    public CourseParticipant getCourseParticipantByPosition(Position position) {
        DetachedCriteria criteria = DetachedCriteria
                .forClass(CourseParticipant.class);
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

    @Override
    public List<CourseParticipant> getEnabledDistinctCourseParticipants(
            Participant participant) {
        return mainDao
                .getQueryResults("select cp from CourseParticipant as cp where cp.enabled=true and cp.participant.pid="
                        + participant.getPid()
                        + " group by cp.participant.pid,cp.course.cid");
    }

    @Override
    public List<CourseParticipant> getEnabledCourseParticipants(
            Participant participant) {
        return mainDao
                .getQueryResults("select cp from CourseParticipant as cp where cp.enabled=true and cp.participant.pid="
                        + participant.getPid());
    }

    @Override
    public List<CourseParticipant> getEnabledCourseParticipants(Course course) {
        return mainDao
                .getQueryResults("select cp from CourseParticipant as cp where cp.enabled=true and cp.course.cid="
                        + course.getCid());
    }

    @Override
    public List<CourseParticipant> getEnabledDistinctCourseParticipants(
            Course course) {
        return mainDao
                .getQueryResults("select cp from CourseParticipant as cp where cp.enabled=true and cp.course.cid="
                        + course.getCid()
                        + " group by cp.participant.pid,cp.course.cid");
    }

}
