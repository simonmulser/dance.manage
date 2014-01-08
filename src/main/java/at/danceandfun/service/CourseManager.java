package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Address;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

public interface CourseManager extends ManagerBase<Course> {

    public List<Course> searchForCourses(Participant actualParticipant,
            String query);

    public List<Course> searchForCourses(Teacher actualTeacher, String query);

    public List<String> getParticipantPerStyle(List<Style> enabledStyles,
            List<CourseParticipant> enabledCourseParticipants, int year);

    public List<Long> getParticipantPerLevel(int year);
    
    public List<Course> getCoursesByStudioAddress(Address address);

    public List<Course> getEnabledCoursesByTeacher(Teacher teacher);
}
