package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.CourseDao;
import at.danceandfun.entity.Course;

@Service
public class CourseManagerImpl extends ManagerBaseImpl<Course> implements
        CourseManager {

    public CourseManagerImpl() {
        super(Course.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(CourseDao courseDao) {
        setDao(courseDao);
    }
}
