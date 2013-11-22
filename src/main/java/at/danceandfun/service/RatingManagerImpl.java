package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Rating;

@Service
public class RatingManagerImpl extends ManagerBaseImpl<Rating> implements
        RatingManager {

    @Autowired
    public void setDao(DaoBaseImpl<Rating> ratingDao) {
        setMainDao(ratingDao);
    }

}
