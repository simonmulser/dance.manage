package at.danceandfun.sat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Performance;
import at.danceandfun.entity.Style;
import at.danceandfun.enumeration.AgeGroup;
import at.danceandfun.enumeration.CourseLevel;
import at.danceandfun.exception.SatException;

public class GenerateSatSolution {

    private int numberOfCourses;
    private int numberOfSlots;
    private int numberOfPlays;
    private ISolver solver;
    private Performance performance;
    private Course dummyCourse;
    private List<Course> newOrderOfCourses;
    private List<Participant> participantList;
    private List<int[]> clauses;
    private int dummies;
    private int movedCourses;
    private List<Integer> swappedCourses;

    /**
     * @precondition An amount of minimal 3 courses as input.
     * @param courses
     * @return Generate a list of all plays, which contain all courses and take
     *         notice of the restrictions.
     * @throws IOException
     */
    public Map<Integer, Performance> generatePerformance(List<Course> courses,
            List<Participant> participantList, boolean balletRestriction,
            boolean twoBreaksRestriction, boolean advancedAtEndRestriction,
            boolean balancedAmountOfSpectators, boolean balancedAgeGroup,
            boolean multipleGroupsSamePerformance, boolean sibsSamePerformance)
            throws IOException, SatException {
        newOrderOfCourses = new ArrayList<Course>();
        performance = new Performance();
        this.participantList = participantList;
        clauses = new ArrayList<int[]>();
        Map<Integer, Performance> plan;
        int[] solution;

        dummyCourse = generateDummyCourse();
        dummies = 0;

        movedCourses = 0;

        List<Course> helpList = new ArrayList<Course>();

        Random r = new Random();

        for (Course c : courses) {
            if (c.getAmountPerformances() == 3) {
                helpList.add(c);
            } else {
                newOrderOfCourses.add(c);
                if (c.getAmountPerformances() == 2) {
                    newOrderOfCourses.add(r.nextInt(newOrderOfCourses.size()),
                            new Course(c));
                }
            }
        }

        for (Course c : helpList) {
            newOrderOfCourses.add(c);
            newOrderOfCourses.add(newOrderOfCourses.size() / 3, c);
            newOrderOfCourses.add(newOrderOfCourses.size() / 3 * 2, c);
        }

        numberOfPlays = 3;

        for (int i = 0; i < newOrderOfCourses.size() % numberOfPlays; i++) {
            newOrderOfCourses.add(((newOrderOfCourses.size() / 3) + 1)
                    * (2 - i), dummyCourse);
            dummies++;
        }

        numberOfCourses = newOrderOfCourses.size();
        numberOfSlots = newOrderOfCourses.size() / numberOfPlays;

        addDummyClauses(newOrderOfCourses);
        addCheckedRestrictions(balletRestriction, twoBreaksRestriction,
                advancedAtEndRestriction, balancedAmountOfSpectators,
                balancedAgeGroup, multipleGroupsSamePerformance,
                sibsSamePerformance);
        addBasicRestrictions(newOrderOfCourses, numberOfCourses, numberOfSlots,
                numberOfPlays);

        solution = executeSingleSAT();
        plan = backMapping(solution, newOrderOfCourses);

        return plan;
    }

    /**
     * @summary This method only adds the checked restrictions from the
     *          PerformanceView to the list of clauses
     * @param balletRestriction
     *            boolean if the restriction for 'nonconsecutive ballets' should
     *            be considered
     * @param twoBreaksRestriction
     *            boolean if the restriction for 'two breaks for students who
     *            perform in multiple courses' should be considered
     * @param advancedAtEndRestriction
     *            boolean if the restriction for 'advanced courses at the end'
     *            should be considered
     * @param balancedAmountOfSpectators
     *            boolean if the restriction for 'balanced amount of spectators'
     *            should be considered
     * @param balancedAgeGroup
     *            boolean if the restriction for "balanced age groups" should be
     *            considered
     * @throws SatException
     */
    private void addCheckedRestrictions(boolean balletRestriction,
            boolean twoBreaksRestriction, boolean advancedAtEndRestriction,
            boolean balancedAmountOfSpectators, boolean balancedAgeGroup,
            boolean multipleGroupsSamePerformance, boolean sibsSamePerformance)
            throws SatException {
        if (multipleGroupsSamePerformance) {
            multipleGroupsInSamePerformance(newOrderOfCourses, participantList);
        }
        if (sibsSamePerformance) {
            sibsInSamePerformance(newOrderOfCourses, participantList);
        }
        if (balancedAmountOfSpectators) {
            arrangeAmountOfSpectators(newOrderOfCourses);
        }
        if (advancedAtEndRestriction) {
            addAdvancedAtTheEnd(newOrderOfCourses, numberOfSlots);
        }
        if (balletRestriction) {
            addNotTwoOfAKind(newOrderOfCourses, numberOfCourses, numberOfSlots,
                    numberOfPlays, movedCourses);
        }
        if (twoBreaksRestriction) {
            add2SlotBrake(newOrderOfCourses, participantList, numberOfCourses,
                    numberOfSlots, numberOfPlays);
        }
        if (balancedAgeGroup) {
            arrangeAgeGroup(newOrderOfCourses);
        }
    }

    /**
     * @precondition Give over the the right amount of plays, time slots and
     *               courses.
     * @example maps the courses in the following layout: XYYZZ, X stands for
     *          the plays (1-3), YY for the time slots (01-99) and ZZ stands for
     *          the courses (01-99). If course 4 dances in the 12th time slot in
     *          the first play its 11204.
     * @param play
     * @param timeslot
     * @param course
     * @return information in the syntax/view of SAT solver
     */
    private int buildMappingVariable(int play, int timeslot, int course) {
        return (play * 10000) + (timeslot * 100) + course;
    }

    /**
     * @summary This method adds the basic restriction, which says, that each
     *          time slot needs one course and that every course has to be used.
     * @param courses
     * @param k
     * @param t
     * @param p
     */
    private void addBasicRestrictions(List<Course> courses, int k, int t, int p) {
        List<Integer> tempList = new ArrayList<Integer>();

        // Jedem Slot muss mindests ein Kurs zugewiesen werden
        // v = Logisches ODER
        // Vi = Einzelne Slots
        // 1,2,3 .. = Kurse
        // (Vi1 v Vi2 v Vi3 ..)
        // for (int i = 1; i <= p; i++) {
        // for (int j = 1; j <= t; j++) {
        // tempList.clear();
        // for (int l = 1; l <= k; l++) {
        // tempList.add(buildMappingVariable(i, j, l));
        //
        // }
        // clauses.add(convertToIntegerArray(tempList));
        // }
        // }

        // Jeder Kurs muss verwendet werden
        // v = Logisches ODER
        // Vi = Einzelne Kurse
        // 1,2,3 .. = Slot
        // (V1i v V2i v V3i ..)
        for (int i = 1; i <= p; i++) {
            for (int j = 1 + (k / 3 * (i - 1)); j <= k / 3 * i; j++) {
                tempList.clear();
                for (int l = 1; l <= t; l++) {
                    tempList.add(buildMappingVariable(i, l, j));
                }
                clauses.add(convertToIntegerArray(tempList));
            }
        }

        // Jedem Slot darf maximal ein Stil zugewiesen werden
        // v = Logisches ODER
        // a = Logisches UND
        // - = Verneinung
        // Vi = Einzelne Slots
        // 1,2,3 .. = Kurse
        // Beispiel für 3 Kurse
        // (-Vi1 v -Vi2) a (-Vi1 v -Vi3) a (-Vi2 v -Vi3)
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= t; j++) {
                for (int l = 1; l < k; l++) {
                    for (int m = 1; m <= k - l; m++) {
                        int[] temp = { -buildMappingVariable(i, j, l),
                                -buildMappingVariable(i, j, l + m) };
                        clauses.add(temp);
                    }
                }
            }
        }
    }

    /**
     * @summary This method adds the restriction, which says, that two courses
     *          of the same kind, are not allowed to stand behind one another.
     * @param courses
     * @param k
     * @param t
     * @param p
     */
    /**
     * @summary This method adds the restriction, which says, that two courses
     *          of the same kind, are not allowed to stand behind one another.
     * @param courses
     * @param k
     * @param t
     * @param p
     */
    private void addNotTwoOfAKind(List<Course> courses, int k, int t, int p,
            int movedCourses) throws SatException {
        List<Integer> tempList = new ArrayList<Integer>();
        List<Integer> listBallets = new ArrayList<Integer>();

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getStyle().getName().equals("Ballett")) {
                listBallets.add(i + 1);
            }
        }

        int countBallets;
        for (int i = 0; i < numberOfPlays; i++) {
            countBallets = 0;
            for (int j = 1; j <= numberOfSlots; j++) {
                for (int ballet : listBallets) {
                    if (ballet == i * numberOfSlots + j) {
                        countBallets++;
                    }
                }
            }
            if (countBallets * 2 - 1 > numberOfSlots - movedCourses) {
                throw new SatException(
                        "Too many Ballets in one Performance! Had to reshuffle!");
            }

        }

        // Der nächste Slot darf nicht den selben Stil beinhalten (nur fuer
        // Ballett relevant)
        // v = Logisches ODER
        // - = Verneinung
        // Vi = Einzelne Slots
        // Vj = Nächster Slot
        // 1 = Stil Ballett
        // (-Vi1 v -Vj1)
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j < t; j++) {
                for (int l = 0; l < listBallets.size(); l++) {
                    int currentBallet = listBallets.get(l);
                    for (int m : listBallets) {
                        if (currentBallet != m) {
                            tempList.clear();
                            tempList.add(-buildMappingVariable(i, j,
                                    currentBallet));
                            tempList.add(-buildMappingVariable(i, j + 1, m));
                            clauses.add(convertToIntegerArray(tempList));
                        }
                    }
                }
            }
        }
    }

    /**
     * @summary This method adds the restriction, which says, that if a
     *          participants dances in more than 1 course, there must be at
     *          least 2 breaks in between.
     * @param courses
     * @param participants
     * @param k
     * @param t
     * @param p
     */
    private void add2SlotBrake(List<Course> courses,
            List<Participant> participants, int k, int t, int p)
            throws SatException {
        List<Integer> tempList = new ArrayList<Integer>();
        List<Integer> idList = new ArrayList<Integer>();
        Participant currentParticipant;

        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getCourseParticipants().size() > 1) {
                idList.add(i);
            }
        }

        for (int id : idList) {
            List<Integer> courseIDList = new ArrayList<Integer>();
            currentParticipant = participants.get(id);

            for (CourseParticipant currentCP : currentParticipant
                    .getCourseParticipants()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(currentCP.getCourse())) {
                        courseIDList.add(i + 1);
                    }
                }
            }

            int countCourses;
            for (int i = 0; i < numberOfPlays; i++) {
                countCourses = 0;
                for (int j = 1; j <= numberOfSlots; j++) {
                    for (int courseID : courseIDList) {
                        if (courseID == i * numberOfSlots + j) {
                            countCourses++;
                        }
                    }
                }
                if (countCourses > (numberOfSlots - 1) / 3) {
                    throw new SatException(
                            "At least one participant dances at too many courses! Had to reshuffle!");
                }

            }
        }

        for (int id : idList) {
            List<Integer> courseIDList = new ArrayList<Integer>();
            currentParticipant = participants.get(id);

            for (CourseParticipant currentCP : currentParticipant
                    .getCourseParticipants()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(currentCP.getCourse())) {
                        courseIDList.add(i + 1);
                    }
                }
            }

            for (int i = 1; i <= p; i++) {
                for (int j = 1; j < t; j++) {
                    for (int l = 0; l < courseIDList.size(); l++) {
                        int currentCourse = courseIDList.get(l);
                        for (int m : courseIDList) {
                            if (currentCourse != m) {
                                tempList.clear();
                                tempList.add(-buildMappingVariable(i, j,
                                        currentCourse));
                                tempList.add(-buildMappingVariable(i, j + 1, m));
                                clauses.add(convertToIntegerArray(tempList));
                                if (j < t - 1) {
                                    tempList.clear();
                                    tempList.add(-buildMappingVariable(i, j,
                                            currentCourse));
                                    tempList.add(-buildMappingVariable(i,
                                            j + 2, m));
                                    clauses.add(convertToIntegerArray(tempList));
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    /**
     * @summary This method puts the courses who are dancing in all 3
     *          performances at the end of each performance
     * @param courses
     * @param t
     */
    private void addAdvancedAtTheEnd(List<Course> courses, int t) {
        Map<String, int[]> usedCourses = new HashMap<String, int[]>();

        /*
         * positions: int 1 = in welche aufführung der jeweilige kurs gemappt
         * werden soll. int 2 = an die wievielte stelle im timeslot der kurs
         * soll. 0 -> 20-0. erster kurs der gemappt wurde. beim wert 1 -> 20-1
         * wird der kurs auf den vorletzten kurs gemappt etc.
         */
        for (int i = 0; i < courses.size(); i++) {
            Course tempCourse = courses.get(i);

            if (tempCourse.getAmountPerformances() == 3) {
                if (usedCourses.containsKey(tempCourse.getName())) {
                    int[] positions = usedCourses.get(tempCourse.getName());
                    positions[0] += 1;
                    int[] temp = { buildMappingVariable(positions[0], t
                            - positions[1], i + 1) };
                    clauses.add(temp);
                    usedCourses.put(tempCourse.getName(), positions);

                } else {
                    int[] temp = { buildMappingVariable(1, t
                            - this.movedCourses, i + 1) };
                    clauses.add(temp);
                    int[] positions = { 1, this.movedCourses };
                    usedCourses.put(tempCourse.getName(), positions);
                    this.movedCourses++;
                }
            }
        }

    }

    private void arrangeAmountOfSpectators(List<Course> courses)
            throws SatException {
        int amount1 = 0;
        int amount2 = 0;
        int amount3 = 0;

        for (int i = 0; i < this.numberOfSlots; i++) {
            amount1 += courses.get(i).getEstimatedSpectators().getValue() + 1;
        }
        for (int i = this.numberOfSlots; i < 2 * this.numberOfSlots; i++) {
            if (!courses.get(i).isDummyCourse()) {
                amount2 += courses.get(i).getEstimatedSpectators().getValue() + 1;
            }
        }
        for (int i = 2 * this.numberOfSlots; i < 3 * this.numberOfSlots; i++) {
            if (!courses.get(i).isDummyCourse()) {
                amount3 += courses.get(i).getEstimatedSpectators().getValue() + 1;
            }
        }

        double meanAmount = (amount1 + amount2 + amount3) / 3;
        if (Math.abs(amount1 - meanAmount) > 3
                || Math.abs(amount2 - meanAmount) > 3
                || Math.abs(amount3 - meanAmount) > 3) {
            throw new SatException("Amount of spectors is not balanced");
        }
    }

    private void arrangeAgeGroup(List<Course> courses) throws SatException {
        int amount1 = 0;
        int amount2 = 0;
        int amount3 = 0;

        for (int i = 0; i < this.numberOfSlots; i++) {
            amount1 += courses.get(i).getAgeGroup().getValue() + 1;
        }
        for (int i = this.numberOfSlots; i < 2 * this.numberOfSlots; i++) {
            if (!courses.get(i).isDummyCourse()) {
                amount2 += courses.get(i).getAgeGroup().getValue() + 1;
            }
        }
        for (int i = 2 * this.numberOfSlots; i < 3 * this.numberOfSlots; i++) {
            if (!courses.get(i).isDummyCourse()) {
                amount3 += courses.get(i).getAgeGroup().getValue() + 1;
            }
        }

        System.out.println("Agegroup 1: " + amount1);
        System.out.println("Agegroup 2: " + amount2);
        System.out.println("Agegroup 3: " + amount3);

        double meanAmount = (amount1 + amount2 + amount3) / 3;
        if (Math.abs(amount1 - meanAmount) > 3
                || Math.abs(amount2 - meanAmount) > 3
                || Math.abs(amount3 - meanAmount) > 3) {
            throw new SatException("Age groups are not balanced");
        }
    }

    /**
     * 
     * @param courses
     * @param participants
     * @param k
     * @param t
     * @param p
     * @throws SatException
     */
    private void multipleGroupsInSamePerformance(List<Course> courses,
            List<Participant> participants) throws SatException {
        List<Integer> idList = new ArrayList<Integer>();
        Random r = new Random();
        swappedCourses = new ArrayList<Integer>();
        swappedCourses.clear();

        // All Participants who play in more than one course.
        for (int i = 0; i < participants.size(); i++) {
            boolean isSinglePerformance = true;
            int counter = 0;

            if (participants.get(i).getCourseParticipants().size() > 1) {
                for (CourseParticipant currentCoPa : participants.get(i)
                        .getCourseParticipants()) {
                    if (currentCoPa.getCourse().isEnabled()) {
                        if (currentCoPa.getCourse().getAmountPerformances() < 2) {
                            counter++;
                            isSinglePerformance = true;

                        } else {
                            isSinglePerformance = false;
                            break;
                        }
                    }
                }
            }
            if (isSinglePerformance && counter > 1) {
                idList.add(i);
                System.out.println(i + ". Teilnehmer namens "
                        + participants.get(i).getFirstname() + " "
                        + participants.get(i).getLastname()
                        + "Anzahl an Kursen: "
                        + participants.get(i).getCourseParticipants().size());
            }
        }

        System.out.println("Anzahl an Participants mit mehr als einem Kurs: "
                + idList.size());

//        System.out.println("Mein Name lautet: "
//                + participants.get(idList.get(0)).getFirstname() + " "
//                + participants.get(idList.get(0)).getLastname());

        // Now count the amount of the courses in the different performances
        for (int id : idList) {
            List<Integer> courseIDList = new ArrayList<Integer>();
            Participant currentParticipant = participants.get(id);
            List<Integer> amount1 = new ArrayList<Integer>();
            List<Integer> amount2 = new ArrayList<Integer>();
            List<Integer> amount3 = new ArrayList<Integer>();

            for (CourseParticipant currentCP : currentParticipant
                    .getCourseParticipants()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(currentCP.getCourse())) {
                        courseIDList.add(i);
                    }
                }
            }
            for (int j = 0; j < courseIDList.size(); j++) {
                if (courseIDList.get(j) < this.numberOfSlots) {
                    amount1.add(courseIDList.get(j));
                }
                if ((2 * this.numberOfSlots) > courseIDList.get(j)
                        && courseIDList.get(j) >= this.numberOfSlots) {
                    amount2.add(courseIDList.get(j));
                }
                if ((3 * this.numberOfSlots) > courseIDList.get(j)
                        && courseIDList.get(j) >= (2 * this.numberOfSlots)) {
                    amount3.add(courseIDList.get(j));
                }
            }

            // normal swapping (when amount of the lists amountX are not even)
            if (amount1.size() > amount2.size()
                    && amount1.size() > amount3.size()) {
                while (amount2.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount2.get(0));
                    Collections.swap(newOrderOfCourses, amount2.get(0),
                            helpSwapping(amount1));
                    amount1.add(amount2.get(0));
                    swappedCourses.add(amount2.remove(0));

                }
                while (amount3.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount3.get(0));
                    Collections.swap(newOrderOfCourses, amount3.get(0),
                            helpSwapping(amount1));
                    amount1.add(amount3.get(0));
                    swappedCourses.add(amount3.remove(0));
                }
            }
            if (amount2.size() > amount1.size()
                    && amount2.size() > amount3.size()) {
                while (amount1.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount1.get(0));
                    Collections.swap(newOrderOfCourses, amount1.get(0),
                            (numberOfSlots + helpSwapping(amount2)));
                    amount2.add(amount1.get(0));
                    swappedCourses.add(amount1.remove(0));
                }
                while (amount3.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount3.get(0));
                    Collections.swap(newOrderOfCourses, amount3.get(0),
                            (numberOfSlots + helpSwapping(amount2)));
                    amount2.add(amount3.get(0));
                    swappedCourses.add(amount3.remove(0));
                }
            }
            if (amount3.size() > amount2.size()
                    && amount3.size() > amount1.size()) {
                while (amount1.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount1.get(0));
                    Collections.swap(newOrderOfCourses, amount1.get(0),
                            (2 * numberOfSlots + helpSwapping(amount3)));
                    amount3.add(amount1.get(0));
                    swappedCourses.add(amount1.remove(0));
                }
                while (amount2.size() != 0) {
                    checkAlreadySwapped(swappedCourses, amount2.get(0));
                    Collections.swap(newOrderOfCourses, amount2.get(0),
                            (2 * numberOfSlots + helpSwapping(amount3)));
                    amount3.add(amount2.get(0));
                    swappedCourses.add(amount2.remove(0));
                }
            }

            // special case, the amount of 2 is even --> random selection
            if (amount1.size() == amount2.size()
                    && amount1.size() != amount3.size()) {
                if (r.nextBoolean()) {
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                (numberOfSlots + helpSwapping(amount2)));
                        amount2.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                    while (amount3.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                (numberOfSlots + helpSwapping(amount2)));
                        amount2.add(amount3.get(0));
                        swappedCourses.add(amount3.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                    while (amount3.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.get(0));
                        swappedCourses.add(amount3.remove(0));
                    }
                }
            }

            if (amount2.size() == amount3.size()
                    && amount2.size() != amount1.size()) {
                if (r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                (2 * numberOfSlots + helpSwapping(amount3)));
                        amount3.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                (2 * numberOfSlots + helpSwapping(amount3)));
                        amount3.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                (numberOfSlots + helpSwapping(amount2)));
                        amount3.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                (numberOfSlots + helpSwapping(amount2)));
                        amount2.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                }
            }
            if (amount1.size() == amount3.size()
                    && amount1.size() != amount2.size()) {
                if (r.nextBoolean()) {
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                (2 * numberOfSlots + helpSwapping(amount3)));
                        amount3.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                (2 * numberOfSlots + helpSwapping(amount3)));
                        amount3.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount3.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.get(0));
                        swappedCourses.add(amount3.remove(0));
                    }
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                }
            }

            if (amount1.size() == amount2.size()
                    && amount1.size() == amount3.size()) {
                int random = (int) (Math.random() * 3 + 1);

                if (random == 1) {
                    while (amount3.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.get(0));
                        swappedCourses.add(amount3.remove(0));
                    }
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                }
                if (random == 2) {
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount3.get(0));
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                (numberOfSlots - 1 + helpSwapping(amount2)));
                        amount3.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                (numberOfSlots - 1 + helpSwapping(amount2)));
                        amount2.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                }
                if (random == 3) {
                    while (amount1.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount1.get(0));
                        Collections
                                .swap(newOrderOfCourses,
                                        amount1.get(0),
                                        (2 * numberOfSlots - 1 + helpSwapping(amount3)));
                        amount3.add(amount1.get(0));
                        swappedCourses.add(amount1.remove(0));
                    }
                    while (amount2.size() != 0) {
                        checkAlreadySwapped(swappedCourses, amount2.get(0));
                        Collections
                                .swap(newOrderOfCourses,
                                        amount2.get(0),
                                        (2 * numberOfSlots - 1 + helpSwapping(amount3)));
                        amount3.add(amount2.get(0));
                        swappedCourses.add(amount2.remove(0));
                    }
                }
            }
        }
    }

    private void sibsInSamePerformance(List<Course> courses,
            List<Participant> participants) throws SatException {
        List<Participant> sibList = new ArrayList<Participant>();
        List<Integer> idList = new ArrayList<Integer>();
        Random r = new Random();

        // All Participants with one sib at least
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getSiblings().size() >= 1) {
                idList.add(i);
            }
        }

        // Now count the amount of the courses in the different performances
        for (int id : idList) {
            List<Integer> courseIDList = new ArrayList<Integer>();
            Participant currentParticipant = participants.get(id);
            List<Integer> amount1 = new ArrayList<Integer>();
            List<Integer> amount2 = new ArrayList<Integer>();
            List<Integer> amount3 = new ArrayList<Integer>();
            boolean allInOne = false;

            for (CourseParticipant currentCP : currentParticipant
                    .getCourseParticipants()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(currentCP.getCourse())) {
                        courseIDList.add(i); // i+1 ??
                    }
                }

            }

            for (Participant participant : currentParticipant.getSiblings()) {
                sibList.add(participant);
            }
            int index = 0;
            for (CourseParticipant currentCP : sibList.get(index)
                    .getCourseParticipants()) {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(currentCP.getCourse())) {
                        courseIDList.add(i + 1); // i+1 ??
                    }
                }
                index++;
            }

            for (int j = 0; j < courseIDList.size(); j++) {
                if (courseIDList.get(j) < this.numberOfSlots) {
                    amount1.add(courseIDList.get(j));
                }
                if ((2 * this.numberOfSlots) > courseIDList.get(j)
                        && courseIDList.get(j) > this.numberOfSlots) {
                    amount2.add(courseIDList.get(j));
                }
                if ((3 * this.numberOfSlots) > courseIDList.get(j)
                        && courseIDList.get(j) > (2 * this.numberOfSlots)) {
                    amount3.add(courseIDList.get(j));
                }
            }

            // normal swapping (when amount of the lists amountX are not even)
            if (amount1.size() > amount2.size()
                    && amount1.size() > amount3.size()) {
                while (amount2.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount2.get(0),
                            helpSwapping(amount1));
                    amount1.add(amount2.remove(0));
                }
                while (amount3.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount3.get(0),
                            helpSwapping(amount1));
                    amount1.add(amount3.remove(0));
                }
            }
            if (amount2.size() > amount1.size()
                    && amount2.size() > amount3.size()) {
                while (amount1.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount1.get(0),
                            2 * helpSwapping(amount2));
                    amount2.add(amount1.remove(0));
                }
                while (amount3.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount3.get(0),
                            2 * helpSwapping(amount2));
                    amount2.add(amount3.remove(0));
                }
            }
            if (amount3.size() > amount2.size()
                    && amount3.size() > amount1.size()) {
                while (amount1.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount1.get(0),
                            3 * helpSwapping(amount3));
                    amount3.add(amount1.remove(0));
                }
                while (amount2.size() != 0) {
                    Collections.swap(newOrderOfCourses, amount2.get(0),
                            3 * helpSwapping(amount3));
                    amount3.add(amount2.remove(0));
                }
            }

            // special case, the amount of 2 is even --> random selection
            if (amount1.size() == amount2.size()
                    && amount1.size() != amount3.size()) {
                if (r.nextBoolean()) {
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                2 * helpSwapping(amount2));
                        amount2.add(amount1.remove(0));
                    }
                    while (amount3.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                2 * helpSwapping(amount2));
                        amount2.add(amount3.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.remove(0));
                    }
                    while (amount3.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.remove(0));
                    }
                }
            }

            if (amount2.size() == amount3.size()
                    && amount2.size() != amount1.size()) {
                if (r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount1.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                2 * helpSwapping(amount2));
                        amount3.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                2 * helpSwapping(amount2));
                        amount2.add(amount1.remove(0));
                    }
                }
            }
            if (amount1.size() == amount3.size()
                    && amount1.size() != amount2.size()) {
                if (r.nextBoolean()) {
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount1.remove(0));
                    }
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount2.remove(0));
                    }
                }
                if (!r.nextBoolean()) {
                    while (amount3.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.remove(0));
                    }
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.remove(0));
                    }
                }
            }

            if (amount1.size() == amount2.size()
                    && amount1.size() == amount3.size()) {
                int random = (int) (Math.random() * 3 + 1);

                if (random == 1) {
                    while (amount3.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount3.remove(0));
                    }
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                helpSwapping(amount1));
                        amount1.add(amount2.remove(0));
                    }
                }
                if (random == 2) {
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount3.get(0),
                                2 * helpSwapping(amount2));
                        amount3.add(amount2.remove(0));
                    }
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                2 * helpSwapping(amount2));
                        amount2.add(amount1.remove(0));
                    }
                }
                if (random == 3) {
                    while (amount1.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount1.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount1.remove(0));
                    }
                    while (amount2.size() != 0) {
                        Collections.swap(newOrderOfCourses, amount2.get(0),
                                3 * helpSwapping(amount3));
                        amount3.add(amount2.remove(0));
                    }
                }
            }
        }
    }

    private int helpSwapping(List<Integer> listOfTakenCourses) {

        System.out.println("1.helpSwapping Liste Elementanzahl: "
                + listOfTakenCourses.size() + " numberOfSlots: "
                + numberOfSlots);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < numberOfSlots; i++) {
            list.add(0);
        }

        for (int j = 0; j < listOfTakenCourses.size(); j++) {
            System.out.println("2.Schleifendurchlauf Nummer: " + j);
            System.out.println("3.list.size() " + list.size()
                    + " numberOfSlots%listOfTakenCourses " + numberOfSlots
                    + "  " + listOfTakenCourses.get(j));
            int restKlasse = listOfTakenCourses.get(j) % numberOfSlots;
            System.out.println("Restklasse " + restKlasse);
            if (list.get(restKlasse) == 0) {
                list.set(listOfTakenCourses.get(j) % numberOfSlots, 1);
            }
            System.out.println("4. Hier?!");
        }

        Random r = new Random();
        int index = r.nextInt(list.size() - 1);
        if (list.get(index) == 1) {
            helpSwappingSecond(list);
        }
        return index;

    }

    private int helpSwappingSecond(List<Integer> list) {
        Random r = new Random();
        int index = r.nextInt(list.size() - 1);
        if (list.get(index) == 1) {
            helpSwappingSecond(list);
        }
        return index;
    }

    private void checkAlreadySwapped(List<Integer> alreadySwapped, int courseID)
            throws SatException {
        for (int i = 0; i < alreadySwapped.size(); i++) {
            if (alreadySwapped.get(i) == courseID) {
                throw new SatException("Der Kurs wurde bereits geswapped!");
            }
        }
    }

    /**
     * @summary Converts the list into a Integer Array, which is necessary to
     *          fill the SAT Solver.
     * @param list
     * @return
     */
    private int[] convertToIntegerArray(List<Integer> list) {
        int[] intArray = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            intArray[i] = list.get(i).intValue();
        }

        return intArray;
    }

    /**
     * @summary Maps back the Integer values to the original expressions like
     *          play, course or time slot.
     * @param solution
     * @param courses
     * @return
     */
    private Map<Integer, Performance> backMapping(int[] solution,
            List<Course> courses) {
        Map<Integer, Performance> plan = new HashMap<Integer, Performance>();
        Performance p1 = new Performance();
        Performance p2 = new Performance();
        Performance p3 = new Performance();
        List<Course> list1 = new ArrayList<Course>();
        List<Course> list2 = new ArrayList<Course>();
        List<Course> list3 = new ArrayList<Course>();
        int perf;
        int course;

        for (int i : solution) {
            if (i > 0) {
                String temp = Integer.toString(i);
                perf = (int) temp.charAt(0) - 48;
                course = Integer.parseInt(temp.substring(3, 5));

                switch (perf) {
                case 1:
                    list1.add(courses.get(course - 1));
                    break;
                case 2:
                    list2.add(courses.get(course - 1));
                    break;
                case 3:
                    list3.add(courses.get(course - 1));
                    break;
                default:
                    System.out
                            .println("INVALID VARIABLE: Invalid performance variable "
                                    + perf
                                    + " "
                                    + course
                                    + " while mapping to courses");
                    break;
                }
            }
        }

        p1.setCourses(list1);
        p2.setCourses(list2);
        p3.setCourses(list3);

        plan.put(1, p1);
        plan.put(2, p2);
        plan.put(3, p3);

        return plan;
    }

    /**
     * @summary Execution of the SAT Solver, finding of solutions.
     * @return
     */
    private int[] executeSingleSAT() {
        solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout

        solver.newVar(buildMappingVariable(numberOfPlays, numberOfSlots,
                numberOfCourses));
        solver.setExpectedNumberOfClauses(clauses.size());

        for (int[] cur : clauses) {
            try {
                solver.addClause(new VecInt(cur));
            } catch (ContradictionException e) {
                if (!e.getMessage().equals("Creating Empty clause ?")) {
                    e.printStackTrace();
                }
            }
        }

        IProblem problem = solver;
        try {
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                int[] model = problem.model();
                return model;
            } else {
                System.out.println("Unsatisfiable !");

            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @summary This method creates a dummy course to fill empty slots
     */
    private Course generateDummyCourse() {
        dummyCourse = new Course();
        Style dummyStyle = new Style();
        dummyStyle.setName(" ");
        // Address address = new Address();
        // address.setStreet("Platzhalter");
        // address.setCity("Platzhalter");
        // address.setZip(10);
        // address.setNumber(10);
        dummyCourse.setName("Dummy");
        dummyCourse.setStyle(dummyStyle);
        dummyCourse.setAmountPerformances(1);
        dummyCourse.setEnabled(true);
        dummyCourse.setLevel(CourseLevel.BEGINNER);
        dummyCourse.setAgeGroup(AgeGroup.SMALL);
        dummyCourse.setDummyCourse(true);
        // dummyCourse.setAddress(address);
        // dummyCourse.setDuration(CourseDuration.FIFTY);
        // dummyCourse.setSemesterPrice(10.0);
        // dummyCourse.setWeekday(WeekDay.MONDAY);

        return dummyCourse;
    }

    /**
     * @summary This method sets the dummy courses to beginning of the
     *          performance
     * @param courses
     */
    private void addDummyClauses(List<Course> courses) {
        int foundDummies = 0;

        if (dummies > 0) {
            for (int i = courses.size() - 1; i >= 0; i--) {
                if (courses.get(i).isDummyCourse()) {
                    if (foundDummies == 0) {
                        int[] temp = { buildMappingVariable(3, 1, i + 1) };
                        clauses.add(temp);
                        foundDummies++;
                    } else if (foundDummies == 1) {
                        int[] temp = { buildMappingVariable(2, 1, i + 1) };
                        clauses.add(temp);
                        foundDummies++;
                    }
                }
                if (foundDummies == dummies) {
                    break;
                }
            }
        }
    }

}
