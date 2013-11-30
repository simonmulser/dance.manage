package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;

public interface CourseManager extends ManagerBase<Course> {

    public List searchForCourses(Participant actualParticipant, String query);
}
