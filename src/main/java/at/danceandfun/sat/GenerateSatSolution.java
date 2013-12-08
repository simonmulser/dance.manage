package at.danceandfun.sat;

import java.io.IOException;
import java.util.ArrayList;
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
import at.danceandfund.exception.SatException;

public class GenerateSatSolution {

    private int numberOfCourses;
    private int numberOfSlots;
    private int numberOfPlays;
    private ISolver solver;
    private Performance performance;
    private List<Course> newOrderOfCourses;
    private List<Participant> participantList;
    private List<int[]> clauses;

    /**
     * @precondition An amount of minimal 3 courses as input.
     * @param courses
     * @return Generate a list of all plays, which contain all courses and take
     *         notice of the restrictions.
     * @throws IOException
     */
    public Map<Integer, Performance> generatePerformance(List<Course> courses,
            List<Participant> participantList) throws IOException, SatException {
        newOrderOfCourses = new ArrayList<Course>();
        performance = new Performance();
        this.participantList = participantList;
        clauses = new ArrayList<int[]>();
        Map<Integer, Performance> plan;
        int[] solution;
        int movedCourses;

        List<Course> helpList = new ArrayList<Course>();

        Random r = new Random();

        for (Course c : courses) {
            if (c.getAmountPerformances() == 3) {
                helpList.add(c);
            } else {
                newOrderOfCourses.add(c);
                if (c.getAmountPerformances() == 2) {
                    newOrderOfCourses.add(r.nextInt(newOrderOfCourses.size()),
                            c);
                }
            }
        }

        for (Course c : helpList) {
            newOrderOfCourses.add(c);
            newOrderOfCourses.add(newOrderOfCourses.size() / 3, c);
            newOrderOfCourses.add(newOrderOfCourses.size() / 3 * 2, c);
        }

        /*
         * Anzahl der verschiedenen Stile und verfügbare Timeslots in der
         * Aufführung Zur Zeit muss die Anzahl der Kurse durch 3 (Anzahl der
         * Aufführungen) teilbar sein. Z.B. 12 Kurse, 3 Aufführungen = 4 Slots
         */
        numberOfCourses = newOrderOfCourses.size();
        numberOfPlays = 3;
        numberOfSlots = newOrderOfCourses.size() / numberOfPlays;

        movedCourses = addAdvancedAtTheEnd(newOrderOfCourses, numberOfSlots);
        addNotTwoOfAKind(newOrderOfCourses, numberOfCourses, numberOfSlots,
                numberOfPlays, movedCourses);
        add2SlotBrake(newOrderOfCourses, participantList, numberOfCourses,
                numberOfSlots, numberOfPlays);
        addBasicRestrictions(newOrderOfCourses, numberOfCourses, numberOfSlots,
                numberOfPlays);

        solution = executeSingleSAT();
        plan = backMapping(solution, newOrderOfCourses);

        return plan;
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
     * @summary This methods adds the basic restriction, which says, that each
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
    private void addNotTwoOfAKind(List<Course> courses, int k, int t, int p,
            int movedCourses) throws SatException {
        List<Integer> tempList = new ArrayList<Integer>();
        List<Integer> listBallets = new ArrayList<Integer>();

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getStyle().getName().equals("Ballet")) {
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
            System.out.println(countBallets);
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
     * @summary This methods adds the restriction, which says, that if a
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
                    if (courses.get(i).equals(currentCP.getKey().getCourse())) {
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
                    if (courses.get(i).equals(currentCP.getKey().getCourse())) {
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
     * @summary This methods puts the courses who are dancing in all 3
     *          performances at the end of each performance
     * @param courses
     * @param t
     */
    private int addAdvancedAtTheEnd(List<Course> courses, int t) {
        Map<String, int[]> usedCourses = new HashMap<String, int[]>();
        int movedCourses = 0;

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
                    int[] temp = { buildMappingVariable(1, t - movedCourses,
                            i + 1) };
                    clauses.add(temp);
                    int[] positions = { 1, movedCourses };
                    usedCourses.put(tempCourse.getName(), positions);
                    movedCourses++;
                }
            }
        }
        return movedCourses;

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
                // TODO Auto-generated catch block
                e.printStackTrace();
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

}
