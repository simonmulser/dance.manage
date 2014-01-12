package at.danceandfun.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.danceandfun.dao.DaoBaseImpl;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Rating;

@Service
public class RatingManagerImpl extends ManagerBaseImpl<Rating> implements
        RatingManager {

    private static Logger logger = Logger.getLogger(RatingManagerImpl.class);

    @Autowired
    public void setDao(DaoBaseImpl<Rating> ratingDao) {
        setMainDao(ratingDao);
    }

    @Override
    public List<Rating> getNewestRatings() {
        List<Rating> ratings = mainDao
                .getQueryResults("Select r from Rating as r where enabled=true order by r.rid desc");
        logger.debug("SIZE: " + ratings.size());
        if (ratings.size() > 4) {
            return ratings.subList(0, 3);
        } else {
            return ratings;
        }

    }

    @Override
    public List<Rating> getEnabledRatings(Participant participant) {
        return mainDao
                .getQueryResults("select r from Rating as r where r.enabled=true and r.participant.pid="
                        + participant.getPid());
    }

}
