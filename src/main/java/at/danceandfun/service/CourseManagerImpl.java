package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;

@Service
public class CourseManagerImpl extends ManagerBaseImpl<Course> implements
        CourseManager {

    @Autowired
    public void setDao(DaoBaseImpl<Course> courseDao) {
        setMainDao(courseDao);
    }

}
