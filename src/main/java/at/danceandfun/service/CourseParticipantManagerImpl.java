package at.danceandfun.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;

@Service
public class CourseParticipantManagerImpl extends
        ManagerBaseImpl<CourseParticipant> implements CourseParticipantManager {

    @Autowired
    public void setDao(DaoBaseImpl<CourseParticipant> courseParticipantDao) {
        setMainDao(courseParticipantDao);
    }

    @Override
    public List<CourseParticipant> getOpenCoursesByParticipant(
            Participant participant) {
        List<CourseParticipant> openCourseParticipants = new ArrayList<CourseParticipant>();
        for (CourseParticipant cp : participant.getCourseParticipants()) {

        }
        return null;
    }

}
