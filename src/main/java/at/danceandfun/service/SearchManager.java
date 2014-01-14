package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

public interface SearchManager {

    public List<Participant> searchParticipants(String query);

    public List<Teacher> searchTeachers(String query);

    public List<Parent> searchParents(String query);

    public List<Course> searchCourses(String query);
}
