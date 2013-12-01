package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

public interface StyleManager extends ManagerBase<Style> {

    public List searchForStyles(Teacher actualTeacher, String query);

    public List searchForStyles(Course actualCourse, String query);
}
