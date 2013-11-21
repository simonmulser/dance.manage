package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Position;

@Service
public class PositionManagerImpl extends ManagerBaseImpl<Position> implements
        PositionManager {

    @Autowired
    public void setDao(DaoBaseImpl<Position> positionDao) {
        setMainDao(positionDao);
    }
}
