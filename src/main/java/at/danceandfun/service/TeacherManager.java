package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Teacher;

public interface TeacherManager extends ManagerBase<Teacher> {

    public List<Teacher> searchForTeachers(Course actualCourse, String query);
}
