package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;

@Service
public class ParentManagerImpl extends ManagerBaseImpl<Parent> implements
        ParentManager {

    @Autowired
    public void setDao(DaoBaseImpl<Parent> parentDao) {
        setMainDao(parentDao);
    }

    public List<Parent> searchForParents(Participant actualParticipant,
            String query) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Parent.class);

        Criterion rest1 = Restrictions.like("firstname", query + "%");
        Criterion rest2 = Restrictions.like("lastname", query + "%");
        criteria.add(Restrictions.or(rest1, rest2));

        criteria.add(Restrictions.eq("enabled", true));

        if (!(actualParticipant.getPid() == null)
                && !(actualParticipant.getParent().getPid() == null)) {
            Criterion rest3 = Restrictions.eq("pid", actualParticipant
                    .getParent().getPid());
            criteria.add(Restrictions.not(rest3));
        }
        List<Parent> parents = mainDao.getListByCriteria(criteria);
        return parents;
    }
}
