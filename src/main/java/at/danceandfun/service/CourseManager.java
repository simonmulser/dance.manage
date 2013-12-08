package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

public interface CourseManager extends ManagerBase<Course> {

    public List<Course> searchForCourses(Participant actualParticipant,
            String query);

    public List<Course> searchForCourses(Teacher actualTeacher, String query);
}
