package at.danceandfun.sat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.Performance;

public class GenerateCNF {

    private final String cnfName = "restrictions.cnf";
    private final Charset encoding = StandardCharsets.UTF_8;
    private ArrayList<int[]> modelList;
    private int numberOfCourses;
    private int numberOfSlots;
    private int numberOfPlays;
    private ISolver solver;
    private Performance performance;
    private List<Course> originalOrderOfCourses;

    public Map<Integer, Performance> generatePerformance(List<Course> courses)
            throws IOException {
        originalOrderOfCourses = courses;
        performance = new Performance();
        List<String> lines = readTextFile(cnfName);
        Map<Integer, Performance> plan;
        int[] solution;

        System.out.println("KURSE------------------------------------");
        System.out.println(courses.toString());

        // Anzahl der verschiedenen Stile und verfügbare Timeslots in der
        // Aufführung
        // Mithilfer der beiden Variablen wird die CNF erstellt
        // Bis 39-39-1 schafft ers noch schnell eine lösung zu finden.
        // Ab 40-40-1 dauerts ein zeitl
        numberOfCourses = originalOrderOfCourses.size();
        numberOfPlays = 3;
        numberOfSlots = originalOrderOfCourses.size() / numberOfPlays;


        // Wenn die gewünschte CNF Datei schon erstellt wurde und keine
        // Parameter geaendert wurden, koennen die beiden Befehle auskommentiert
        // werden.
        // Wenn die ausgefuehrt werden, muss die CNF Datei vor dem naechsten
        // Programmstart manuel entleert werden
        // buildTimeslots(lines, originalOrderOfCourses, numberOfCourses,
        // numberOfSlots, numberOfPlays);
        // addHeader(lines, numberOfCourses, numberOfSlots);

        // modelList = executeSAT(cnfName);
        solution = executeSingleSAT(cnfName);
        plan = mapping(solution, originalOrderOfCourses);

        return plan;
    }

    List<String> readTextFile(String fileName) throws IOException {
        // Path path = Paths.get(fileName);
        // return Files.readAllLines(path, encoding);
        InputStream is = GenerateCNF.class
.getResourceAsStream(fileName);
        return new ArrayList<String>();
    }

    void writeTextFile(List<String> lines, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Files.write(path, lines, encoding);
    }

    // Mappt die Kurse in der Form XYYZZ
    // X = Aufführung (1 .. 3)
    // YY = Timeslot (01 .. 99)
    // ZZ = Kurs (01 .. 99)
    // Beispiel: Kurs 4 tanzt im 12. Timeslot der 1. Aufführung
    // 11204
    int variable(int play, int timeslot, int course) {
        return (play * 10000) + (timeslot * 100) + course;
    }

    // In Abhaengikeit der Anzahl an Stile und Timeslots wird mithilfe von 3
    // Formelen die
    // Notwendige CNF Datei erstellt.
    void buildTimeslots(List<String> lines, List<Course> courses, int k, int t,
            int p)
            throws IOException {
        StringBuffer sbClause = new StringBuffer();
        String clause = "";

        // Jedem Slot muss mindests ein Kurs zugewiesen werden
        // v = Logisches ODER
        // Vi = Einzelne Slots
        // 1,2,3 .. = Kurse
        // (Vi1 v Vi2 v Vi3 ..)
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= t; j++) {
                sbClause = new StringBuffer();
                for (int l = 1; l <= k; l++) {
                    sbClause.append(variable(i, j, l) + " ");
                }
                lines.add(sbClause.toString() + "0");
            }
        }

        // Jeder Kurs muss verwendet werden
        // v = Logisches ODER
        // Vi = Einzelne Kurse
        // 1,2,3 .. = Slot
        // (V1i v V2i v V3i ..)
        for (int i = 1; i <= p; i++) {
            for (int j = 1 + (k / 3 * (i - 1)); j <= k / 3 * i; j++) {
                sbClause = new StringBuffer();
                for (int l = 1; l <= t; l++) {
                    sbClause.append(variable(i, l, j) + " ");
                }
                lines.add(sbClause.toString() + "0");
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
        // for (int i = 1; i <= p; i++) {
        // for (int j = 1; j < t; j++) {
        // for (int l = 1; l <= numberOfBallet; l++) {
        // clause = -(variable(i, j, l)) + " "
        // + -(variable(p, j + 1, l)) + " 0";
        // lines.add(clause);
        // }
        // }
        // }

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
                        lines.add(-variable(i, j, l) + " "
                                + -variable(i, j, l + m) + " 0");
                    }
                }
            }
        }

        writeTextFile(lines, cnfName);
    }

    // Es wird ein Header fuer die erstellte CNF Datei hinzugefuegt. Dieser
    // beinhaltet die Anzahl an
    // Variablen und Klauseln
    void addHeader(List<String> lines, int k, int t) throws IOException {
        String header = "p cnf "
                + variable(numberOfPlays, numberOfSlots, numberOfCourses) + " "
                + lines.size();

        lines.add(0, header);

        writeTextFile(lines, cnfName);
    }

    private Map<Integer, Performance> mapping(int[] solution,
            List<Course> courses) {
        Map<Integer, Performance> plan = new HashMap<Integer, Performance>();
        Performance p1 = new Performance();
        Performance p2 = new Performance();
        Performance p3 = new Performance();
        List<Course> list1 = new ArrayList<Course>();
        List<Course> list2 = new ArrayList<Course>();
        List<Course> list3 = new ArrayList<Course>();
        int perf;
        // int slot;
        int course;
        
        for (int i : solution) {
            if (i > 0) {
                String temp = Integer.toString(i);
                perf = (int) temp.charAt(0) - 48;
                // slot = Integer.parseInt(temp.substring(1, 3));
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

    // ArrayList<int[]> executeSAT(String fileName) {
    // solver = SolverFactory.newDefault();
    // ModelIterator mi = new ModelIterator(solver);
    // solver.setTimeout(600); // 10 min timeout
    // Reader reader = new InstanceReader(mi);
    // ArrayList<int[]> models = new ArrayList<int[]>();
    //
    // try {
    // boolean unsat = true;
    // IProblem problem = reader.parseInstance(fileName);
    //
    // int counter = 0;
    // while (problem.isSatisfiable()) {
    // if (counter == 3) {
    // break;
    // }
    // unsat = false;
    // int[] model = problem.model();
    // models.add(model);
    // System.out.println(Arrays.toString(model));
    // counter++;
    // }
    // if (unsat) {
    // System.out.println("Unsatisfiable!");
    // }
    // } catch (FileNotFoundException e) {
    // e.printStackTrace();
    // } catch (ParseFormatException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // } catch (ContradictionException e) {
    // System.out.println("Unsatisfiable (trivial)!");
    // } catch (TimeoutException e) {
    // System.out.println("Timeout, sorry!");
    // }
    // return models;
    // }
    //
    private int[] executeSingleSAT(String fileName) {
        solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout

        Reader reader = new DimacsReader(solver);

        try {
            IProblem problem = reader.parseInstance(GenerateCNF.class
                    .getResourceAsStream(fileName));
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                int[] model = problem.model();
                System.out.println(Arrays.toString(model));
                return model;
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }
        return null;
    }

}
