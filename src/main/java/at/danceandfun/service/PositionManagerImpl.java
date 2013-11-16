package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.PositionDao;
import at.danceandfun.entity.Position;

@Service
public class PositionManagerImpl extends ManagerBaseImpl<Position> implements
        PositionManager {

    private PositionDao positionDao;

    @Autowired
    public void initializeDao(PositionDao positionDao) {
        this.positionDao = positionDao;
        setDao(positionDao);
    }
}
