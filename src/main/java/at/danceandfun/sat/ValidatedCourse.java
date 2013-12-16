package at.danceandfun.sat;

import at.danceandfun.entity.Course;

/**
 * true = valiated
 * 
 */
public class ValidatedCourse {
    private Course course;
    private Boolean balletRestriktion;
    private Boolean twoBreaksRestriktion;
    private Boolean advancedAtEndRestriktion;

    public ValidatedCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getBalletRestriktion() {
        return balletRestriktion;
    }

    public void setBalletRestriktion(Boolean balletRestriktion) {
        this.balletRestriktion = balletRestriktion;
    }

    public Boolean getTwoBreaksRestriktion() {
        return twoBreaksRestriktion;
    }

    public void setTwoBreaksRestriktion(Boolean twoBreaksRestriktion) {
        this.twoBreaksRestriktion = twoBreaksRestriktion;
    }

    public Boolean getAdvancedAtEndRestriktion() {
        return advancedAtEndRestriktion;
    }

    public void setAdvancedAtEndRestriktion(Boolean advancedAtEndRestriktion) {
        this.advancedAtEndRestriktion = advancedAtEndRestriktion;
    }

}
