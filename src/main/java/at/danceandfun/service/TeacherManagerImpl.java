package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.TeacherDao;
import at.danceandfun.entity.Teacher;

@Service
public class TeacherManagerImpl extends ManagerBaseImpl<Teacher> implements
        TeacherManager {

    private TeacherDao teacherDao;

    public TeacherManagerImpl() {
        super(Teacher.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
        setDao(teacherDao);
    }

    public List<Teacher> getActiveList() {
        DetachedCriteria active = DetachedCriteria.forClass(Teacher.class);
        active.add(Restrictions.eq("active", true));
        return teacherDao.getListByCriteria(active);
    }

}
