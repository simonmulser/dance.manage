package at.danceandfun.service;

import java.util.List;

import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Rating;

public interface RatingManager extends ManagerBase<Rating> {

    public List<Rating> getNewestRatings();

    public List<Rating> getEnabledRatings(Participant participant);

}
