package at.danceandfun.util;

import java.util.ArrayList;
import java.util.List;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.EntityBase;
import at.danceandfun.entity.Parent;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Teacher;

public class SearchResult {

    private List<Participant> searchedParticipants;
    private List<Parent> searchedParents;
    private List<Teacher> searchedTeachers;
    private List<Course> searchedCourses;

    public List<Participant> getSearchedParticipants() {
        return searchedParticipants;
    }

    public void setSearchedParticipants(List<Participant> searchedParticipants) {
        this.searchedParticipants = searchedParticipants;
    }

    public List<Parent> getSearchedParents() {
        return searchedParents;
    }

    public void setSearchedParents(List<Parent> searchedParents) {
        this.searchedParents = searchedParents;
    }

    public List<Teacher> getSearchedTeachers() {
        return searchedTeachers;
    }

    public void setSearchedTeachers(List<Teacher> searchedTeachers) {
        this.searchedTeachers = searchedTeachers;
    }

    public List<Course> getSearchedCourses() {
        return searchedCourses;
    }

    public void setSearchedCourses(List<Course> searchedCourses) {
        this.searchedCourses = searchedCourses;
    }

    public List<EntityBase> getAllResults() {
        List<EntityBase> allSearchResults = new ArrayList<EntityBase>();
        allSearchResults.addAll(getSearchedParticipants());
        allSearchResults.addAll(getSearchedParents());
        allSearchResults.addAll(getSearchedTeachers());
        allSearchResults.addAll(getSearchedCourses());

        return allSearchResults;
    }
}
