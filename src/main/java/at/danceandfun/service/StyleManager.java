package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

public interface StyleManager extends ManagerBase<Style> {

    public List searchForStyles(Teacher actualTeacher, String query);
}
