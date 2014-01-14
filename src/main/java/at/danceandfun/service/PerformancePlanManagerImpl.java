package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.PerformancePlan;

@Service
public class PerformancePlanManagerImpl extends
        ManagerBaseImpl<PerformancePlan> implements PerformancePlanManager {

    @Autowired
    public void setDao(DaoBaseImpl<PerformancePlan> performancePlanDao) {
        setMainDao(performancePlanDao);
    }

}
