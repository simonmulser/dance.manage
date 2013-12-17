package at.danceandfun.sat;

import at.danceandfun.entity.Course;

/**
 * true = valiated
 * 
 */
public class ValidatedCourse {
    private Course course;
    private Boolean balletRestriction;
    private Boolean twoBreaksRestriction;
    private Boolean advancedAtEndRestriction;

    public ValidatedCourse(Course course) {
        this.course = course;
        this.balletRestriction = false;
        this.twoBreaksRestriction = false;
        this.advancedAtEndRestriction = false;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getBalletRestriction() {
        return balletRestriction;
    }

    public void setBalletRestriction(Boolean balletRestriction) {
        this.balletRestriction = balletRestriction;
    }

    public Boolean getTwoBreaksRestriction() {
        return twoBreaksRestriction;
    }

    public void setTwoBreaksRestriction(Boolean twoBreaksRestriction) {
        this.twoBreaksRestriction = twoBreaksRestriction;
    }

    public Boolean getAdvancedAtEndRestriction() {
        return advancedAtEndRestriction;
    }

    public void setAdvancedAtEndRestriction(Boolean advancedAtEndRestriction) {
        this.advancedAtEndRestriction = advancedAtEndRestriction;
    }

}
