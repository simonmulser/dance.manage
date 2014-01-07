package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.CourseParticipant;

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

}
