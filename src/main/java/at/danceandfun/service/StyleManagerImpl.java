package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.StyleDao;
import at.danceandfun.entity.Style;

@Service
public class StyleManagerImpl extends ManagerBaseImpl<Style> implements
        StyleManager {

    private StyleDao styleDao;

    public StyleManagerImpl() {
        super(Style.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(StyleDao styleDao) {
        this.styleDao = styleDao;
        setDao(styleDao);
    }

    public List<Style> getActiveList() {
        DetachedCriteria active = DetachedCriteria.forClass(Style.class);
        active.add(Restrictions.eq("active", true));
        return styleDao.getListByCriteria(active);
    }

}
