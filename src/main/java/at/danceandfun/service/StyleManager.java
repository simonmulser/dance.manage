package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

public interface StyleManager extends ManagerBase<Style> {

    public List<Style> searchForStyles(Teacher actualTeacher, String query);

    public List<Style> searchForStyles(Course actualCourse, String query);
}
