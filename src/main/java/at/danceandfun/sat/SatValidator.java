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

/**
 * This class works as a validator for the SAT solver. The input parameters
 * contain of the solution from the solver and the list of participants. In the
 * process of validating the solution, specific boolean variables are set for
 * every course and every restriction. The class ValidatedCourse works as a link
 * to store the restriction booleans for every course.
 * 
 */

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

    /**
     * @summary Every list of courses is mirrored into a list of
     *          ValidatedCourses. The advantage of this list is the option to
     *          set booleans for restrictions for every course.
     */
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

    /**
     * @summary Main method of the Validator. In here, all the methods for the
     *          restrictions are called.
     * @return 3 lists of ValidatedCourses. Each for one performance. Booleans
     *         for the restrictions are stored for every course.
     */
    public Map<Integer, List<ValidatedCourse>> validatePerformancePlan() {
        // Restriction - Not two of a kind
        validatedCourseList1 = validateNotTwoOfAKindRestriktion(validatedCourseList1);
        validatedCourseList2 = validateNotTwoOfAKindRestriktion(validatedCourseList2);
        validatedCourseList3 = validateNotTwoOfAKindRestriktion(validatedCourseList3);

        // Restriction - 2 slots brake
        validatedCourseList1 = validate2SlotBrakeRestriktion(
                validatedCourseList1, participantList);
        validatedCourseList2 = validate2SlotBrakeRestriktion(
                validatedCourseList2, participantList);
        validatedCourseList3 = validate2SlotBrakeRestriktion(
                validatedCourseList3, participantList);

        // Restriction - Advanced at the end
        validatedCourseList1 = validateAdvancedAtTheEndRestriktion(validatedCourseList1);
        validatedCourseList2 = validateAdvancedAtTheEndRestriktion(validatedCourseList2);
        validatedCourseList3 = validateAdvancedAtTheEndRestriktion(validatedCourseList3);

        validatedMap.put(1, validatedCourseList1);
        validatedMap.put(2, validatedCourseList2);
        validatedMap.put(3, validatedCourseList3);

        return validatedMap;
    }

    /**
     * @summary This method validates the restriction for nonconsecutive
     *          ballets. Every course is inspected and the boolean for the
     *          specific restriction is set to 'true' if the constraint is
     *          violated
     * @param validatedCourseList
     *            Gets a list of ValidatedCourses as an input. This class
     *            contains a course and booleans for the violation of
     *            restrictions
     * @return list of ValidatedCourses with the updated booleans of
     *         balletRestriction
     */
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

    /**
     * @summary This method validates the restriction for 2 courses brake for
     *          participants that dance in both courses. Every course is
     *          inspected and the boolean for the specific restriction is set to
     *          'true' if the constraint is violated
     * @param validatedCourseList
     *            Gets a list of ValidatedCourses as an input. This class
     *            contains a course and booleans for the violation of
     *            restrictions
     * @return list of ValidatedCourses with the updated booleans of
     *         twoBreaksRestriction
     */
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
                            .equals(currentCP.getCourse())) {
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

    /**
     * @summary This method validates the restriction for advanced courses have
     *          to dance at the end of the performance. Every course is
     *          inspected and the boolean for the specific restriction is set to
     *          'true' if the constraint is violated
     * @param validatedCourseList
     *            Gets a list of ValidatedCourses as an input. This class
     *            contains a course and booleans for the violation of
     *            restrictions
     * @return list of ValidatedCourses with the updated booleans of
     *         advancedAtEndRestriction
     */
    private List<ValidatedCourse> validateAdvancedAtTheEndRestriktion(
            List<ValidatedCourse> validatedCourseList) {
        List<Integer> idList = new ArrayList<Integer>();
        int sizeOfValidatedCourseList = validatedCourseList.size();

        for (int i = 0; i < sizeOfValidatedCourseList; i++) {
            if (validatedCourseList.get(i).getCourse().getAmountPerformances() == 3) {
                idList.add(i);
            }
        }

        Collections.sort(idList);
        Collections.reverse(idList);

        for (int i = 0; i < idList.size(); i++) {
            if (!(idList.get(i) == sizeOfValidatedCourseList - (i + 1))) {
                validatedCourseList.get(idList.get(i))
                        .setAdvancedAtEndRestriction(true);
                violationOfRestriktions = true;
            }
        }

        return validatedCourseList;
    }

    public Boolean getViolationOfRestriktions() {
        return this.violationOfRestriktions;
    }
}
