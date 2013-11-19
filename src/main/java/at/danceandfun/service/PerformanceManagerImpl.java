package at.danceandfun.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.PerformanceDao;
import at.danceandfun.entity.Performance;

@Service
public class PerformanceManagerImpl extends ManagerBaseImpl<Performance>
        implements PerformanceManager {

    private PerformanceDao performanceDao;

    public PerformanceManagerImpl() {
        super(Performance.class);
        // TODO Auto-generated constructor stub
    }

    @Autowired
    public void initializeDao(PerformanceDao performanceDao) {
        this.performanceDao = performanceDao;
        setDao(performanceDao);
    }

    public List<Performance> getActiveList() {
        DetachedCriteria active = DetachedCriteria.forClass(Performance.class);
        active.add(Restrictions.eq("active", true));
        return performanceDao.getListByCriteria(active);
    }

}
