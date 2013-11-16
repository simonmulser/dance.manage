package at.danceandfun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.RatingDao;
import at.danceandfun.entity.Rating;

@Service
public class RatingManagerImpl extends ManagerBaseImpl<Rating> implements
        RatingManager {

    private RatingDao ratingDao;

    @Autowired
    public void initializeDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
        setDao(ratingDao);
    }
}
