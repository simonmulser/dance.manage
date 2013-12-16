package at.danceandfun.sat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Performance;

public class SatValidator {

    private Boolean violationOfRestriktions;
    private Map<Integer, Performance> performancePlan;
    private Map<Integer, List<ValidatedCourse>> validatedMap;
    private List<Course> courseList1;
    private List<Course> courseList2;
    private List<Course> courseList3;
    private List<ValidatedCourse> validatedCourseList1;
    private List<ValidatedCourse> validatedCourseList2;
    private List<ValidatedCourse> validatedCourseList3;

    public SatValidator(Map<Integer, Performance> performancePlan) {
        this.violationOfRestriktions = false;
        this.performancePlan = performancePlan;

        this.courseList1 = performancePlan.get(1).getCourses();
        this.courseList2 = performancePlan.get(2).getCourses();
        this.courseList3 = performancePlan.get(3).getCourses();

        this.validatedCourseList1 = new ArrayList<ValidatedCourse>();
        this.validatedCourseList2 = new ArrayList<ValidatedCourse>();
        this.validatedCourseList3 = new ArrayList<ValidatedCourse>();

        this.validatedMap = new HashMap<Integer, List<ValidatedCourse>>();

        fillValidatedCourseLists();

    }

    private void fillValidatedCourseLists() {
        for (Course currentCourse : this.courseList1) {
            this.validatedCourseList1.add(new ValidatedCourse(currentCourse));
        }
        for (Course currentCourse : this.courseList2) {
            this.validatedCourseList2.add(new ValidatedCourse(currentCourse));
        }
        for (Course currentCourse : this.courseList3) {
            this.validatedCourseList3.add(new ValidatedCourse(currentCourse));
        }
    }

    public Map<Integer, List<ValidatedCourse>> validatePerformancePlan() {
        validatedCourseList1 = validateNotTwoOfAKindRestriktion(courseList1,
                validatedCourseList1);
        validatedCourseList2 = validateNotTwoOfAKindRestriktion(courseList2,
                validatedCourseList2);
        validatedCourseList3 = validateNotTwoOfAKindRestriktion(courseList3,
                validatedCourseList3);

        validatedMap.put(1, validatedCourseList1);
        validatedMap.put(2, validatedCourseList2);
        validatedMap.put(3, validatedCourseList3);

        return validatedMap;
    }

    private List<ValidatedCourse> validateNotTwoOfAKindRestriktion(
            List<Course> courseList, List<ValidatedCourse> validatedCourseList) {

        int countGap = 999;
        int lastBallet = -1;

        for (int i = 0; i < validatedCourseList.size(); i++) {
            Course currentCourse = validatedCourseList.get(i).getCourse();
            if (currentCourse.getStyle().getName().equals("Ballett")) {
                if (countGap == 0) {
                    if (lastBallet != -1) {
                        validatedCourseList.get(lastBallet)
                                .setBalletRestriktion(true);
                    }
                    validatedCourseList.get(i).setBalletRestriktion(true);
                    violationOfRestriktions = true;

                }
                lastBallet = i;
                countGap = 0;
            } else {
                validatedCourseList.get(i).setBalletRestriktion(false);
                countGap++;
            }
        }

        return validatedCourseList;
    }

    private void validate2SlotBrakeRestriktion(List<Course> courseList) {

    }

    private void validateAdvancedAtTheEndRestriktion(List<Course> courseList) {

    }

    public Boolean getViolationOfRestriktions() {
        return this.violationOfRestriktions;
    }
}
