package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Teacher;

@Service
public class TeacherManagerImpl extends ManagerBaseImpl<Teacher> implements
        TeacherManager {

    @Autowired
    public void setDao(DaoBaseImpl<Teacher> teacherDao) {
        setMainDao(teacherDao);
    }
}
