package at.danceandfun.sat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Performance;

public class SatValidator {

    private Boolean violationOfRestriktions;
    private Map<Integer, Performance> performancePlan;
    private Map<Integer, List<ValidatedCourse>> validatedMap;
    private List<Participant> participantList;
    private List<Course> courseList1;
    private List<Course> courseList2;
    private List<Course> courseList3;
    private List<ValidatedCourse> validatedCourseList1;
    private List<ValidatedCourse> validatedCourseList2;
    private List<ValidatedCourse> validatedCourseList3;

    public SatValidator(Map<Integer, Performance> performancePlan,
            List<Participant> participantList) {
        this.violationOfRestriktions = false;
        this.performancePlan = performancePlan;
        this.participantList = participantList;

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
        validatedCourseList1 = validateNotTwoOfAKindRestriktion(validatedCourseList1);
        validatedCourseList2 = validateNotTwoOfAKindRestriktion(validatedCourseList2);
        validatedCourseList3 = validateNotTwoOfAKindRestriktion(validatedCourseList3);

        validatedCourseList1 = validate2SlotBrakeRestriktion(
                validatedCourseList1, participantList);
        validatedCourseList2 = validate2SlotBrakeRestriktion(
                validatedCourseList2, participantList);
        validatedCourseList3 = validate2SlotBrakeRestriktion(
                validatedCourseList3, participantList);

        validatedMap.put(1, validatedCourseList1);
        validatedMap.put(2, validatedCourseList2);
        validatedMap.put(3, validatedCourseList3);

        return validatedMap;
    }

    private List<ValidatedCourse> validateNotTwoOfAKindRestriktion(
            List<ValidatedCourse> validatedCourseList) {

        int countGap = 999;
        int lastBallet = -1;

        for (int i = 0; i < validatedCourseList.size(); i++) {
            Course currentCourse = validatedCourseList.get(i).getCourse();
            if (currentCourse.getStyle().getName().equals("Ballett")) {
                if (countGap == 0) {
                    if (lastBallet != -1) {
                        validatedCourseList.get(lastBallet)
                                .setBalletRestriction(true);
                    }
                    validatedCourseList.get(i).setBalletRestriction(true);
                    violationOfRestriktions = true;

                }
                lastBallet = i;
                countGap = 0;
            } else {
                countGap++;
            }
        }

        return validatedCourseList;
    }

    private List<ValidatedCourse> validate2SlotBrakeRestriktion(
            List<ValidatedCourse> validatedCourseList,
            List<Participant> participantList) {
        List<Integer> participantIDList = new ArrayList<Integer>();
        List<Integer> courseIDList = new ArrayList<Integer>();
        Participant currentParticipant;

        for (int i = 0; i < participantList.size(); i++) {
            if (participantList.get(i).getCourseParticipants().size() > 1) {
                participantIDList.add(i);
            }
        }

        for (int id : participantIDList) {
            courseIDList.clear();
            currentParticipant = participantList.get(id);

            for (CourseParticipant currentCP : currentParticipant
                    .getCourseParticipants()) {
                for (int i = 0; i < validatedCourseList.size(); i++) {
                    if (validatedCourseList.get(i).getCourse()
                            .equals(currentCP.getKey().getCourse())) {
                        courseIDList.add(i);
                    }
                }
            }

            Collections.sort(courseIDList);

            if (courseIDList.size() > 1) {
                for (int i = 0; i < courseIDList.size() - 1; i++) {
                    if (!(courseIDList.get(i) + 2 < courseIDList.get(i + 1))) {
                        validatedCourseList.get(courseIDList.get(i))
                                .setTwoBreaksRestriction(true);
                        validatedCourseList.get(courseIDList.get(i + 1))
                                .setTwoBreaksRestriction(true);
                        violationOfRestriktions = true;
                    }
                }
            }
        }

        return validatedCourseList;
    }

    private void validateAdvancedAtTheEndRestriktion(List<Course> courseList) {

    }

    public Boolean getViolationOfRestriktions() {
        return this.violationOfRestriktions;
    }
}
