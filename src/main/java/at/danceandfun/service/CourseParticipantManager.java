package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Position;

public interface CourseParticipantManager extends
        ManagerBase<CourseParticipant> {

    public int getCourseCountByParticipant(int cid, int pid);

    public CourseParticipant getCourseParticipantByPosition(Position position);

    public List<CourseParticipant> getEnabledDistinctCourseParticipants(
            Participant participant);

    public List<CourseParticipant> getEnabledCourseParticipants(
            Participant participant);

    public List<CourseParticipant> getEnabledDistinctCourseParticipants(
            Course course);

    public List<CourseParticipant> getEnabledCourseParticipants(Course course);

    public List<CourseParticipant> getCourseParticipantsByCount();
}
