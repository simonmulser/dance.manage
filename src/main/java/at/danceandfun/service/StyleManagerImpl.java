package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Course;
import at.danceandfun.entity.Style;
import at.danceandfun.entity.Teacher;

@Service
public class StyleManagerImpl extends ManagerBaseImpl<Style> implements
        StyleManager {

    @Autowired
    public void setDao(DaoBaseImpl<Style> styleDao) {
        setMainDao(styleDao);
    }

    public List<Style> searchForStyles(Teacher actualTeacher, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Style.class);

        Criterion rest1 = Restrictions.like("name", query + "%");
        criteria.add(rest1);

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualTeacher.getPid() == null)) {
            for (Style s : actualTeacher.getStyles()) {
                Criterion rest2 = Restrictions.eq("sid", s.getSid());
                criteria.add(Restrictions.not(rest2));
            }
        }
        List<Style> styles = mainDao.getListByCriteria(criteria);
        return styles;
    }

    public List<Style> searchForStyles(Course actualCourse, String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Style.class);

        Criterion rest1 = Restrictions.like("name", query + "%");
        criteria.add(rest1);

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualCourse.getCid() == null)
                && !(actualCourse.getStyle().getSid() == null)) {
            Criterion rest2 = Restrictions.eq("sid", actualCourse.getStyle()
                    .getSid());
            criteria.add(Restrictions.not(rest2));
        }
        List<Style> styles = mainDao.getListByCriteria(criteria);
        return styles;
    }

}
