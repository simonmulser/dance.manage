package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Teacher;

public interface TeacherManager extends ManagerBase<Teacher> {

    public List<Teacher> getActiveList();
}
