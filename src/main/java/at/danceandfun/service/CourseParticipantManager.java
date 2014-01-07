package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;

public interface CourseParticipantManager extends
        ManagerBase<CourseParticipant> {

    public List<CourseParticipant> getOpenCoursesByParticipant(
            Participant participant);

}
