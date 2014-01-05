package at.danceandfun.service;

import at.danceandfun.entity.CourseParticipant;

public interface CourseParticipantManager extends
        ManagerBase<CourseParticipant> {

    public int getCourseCountByParticipant(int cid, int pid);

}
