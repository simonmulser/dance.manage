package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Performance;

@Service
public class PerformanceManagerImpl extends ManagerBaseImpl<Performance>
        implements PerformanceManager {

    @Autowired
    public void setDao(DaoBaseImpl<Performance> performanceDao) {
        setMainDao(performanceDao);
    }

}
