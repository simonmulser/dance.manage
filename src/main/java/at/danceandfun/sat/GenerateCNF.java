package at.danceandfun.sat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

public class GenerateCNF {

    private final String cnfName = "restrictions.cnf";
    private final Charset encoding = StandardCharsets.UTF_8;
    private ArrayList<int[]> modelList;
    private int numberOfCourses;
    private int numberOfSlots;
    private int numberOfPlays;
    private int numberOfHipHop;
    private int numberOfRock;
    private int numberOfBallet;
    private int numberOfStep;
    private int numberOfClassic;
    private ISolver solver;

    public void main() throws IOException {
        GenerateCNF gen = new GenerateCNF();
        List<String> lines = gen.readTextFile(cnfName);

        // Anzahl der verschiedenen Stile und verfügbare Timeslots in der
        // Aufführung
        // Mithilfer der beiden Variablen wird die CNF erstellt
        // Bis 39-39-1 schafft ers noch schnell eine lösung zu finden.
        // Ab 40-40-1 dauerts ein zeitl
        numberOfCourses = 12;
        numberOfSlots = 4;
        numberOfPlays = 3;

        // Anzahl der Kurse pro Stil
        // Die erste 2 Kurse sind vom Stil Ballett
        numberOfBallet = 10;
        // numberOfRock = 2;
        // numberOfClassic = 1;
        // numberOfHipHop = 0;
        // numberOfStep = 0;

        // Wenn die gewünschte CNF Datei schon erstellt wurde und keine
        // Parameter geaendert wurden, koennen die beiden Befehle auskommentiert
        // werden.
        // Wenn die ausgefuehrt werden, muss die CNF Datei vor dem naechsten
        // Programmstart manuel entleert werden
        gen.buildTimeslots(lines, numberOfCourses, numberOfSlots, numberOfPlays);
        gen.addHeader(lines, numberOfCourses, numberOfSlots);

        modelList = gen.executeSAT(cnfName);
        // gen.executeSingleSAT(cnfName);
        // gen.assignStyle(modelList).toString();

    }

    List<String> readTextFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllLines(path, encoding);
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
    void buildTimeslots(List<String> lines, int k, int t, int p)
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
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j < t; j++) {
                for (int l = 1; l <= numberOfBallet; l++) {
                    clause = -(variable(i, j, l)) + " "
                            + -(variable(p, j + 1, l)) + " 0";
                    lines.add(clause);
                }
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

    // Die vom SAT Solver verwendeten Variablen werden auf die vorhandenen Stile
    // gemappt
    // public ArrayList<String> assignStyle(ArrayList<int[]> modelList) {
    // ArrayList<String> newModel = new ArrayList<String>();
    // // zaehle mit wie oft ein Stil in einer Loesung vorkommt
    // int countHipHop = 0;
    // int countRock = 0;
    // int countClassic = 0;
    // int countBallet = 0;
    // int countStep = 0;
    //
    // // gehe die Liste mit allen Loesungen der Reihe nach durch
    // for (int[] model : modelList) {
    // StringBuffer sequence = new StringBuffer();
    //
    // // setze den Counter fuer die neue Loesung auf 0
    // countHipHop = countRock = countClassic = countBallet = countStep = 0;
    //
    // for (int i = 0; i < numberOfCourses * numberOfSlots; i++) {
    // int style;
    //
    // // verwende nur Variablen denen "true" zugewiesen wurde
    // if (model[i] > 0) {
    //
    // // mapping auf die Stile
    // if ((model[i] % numberOfCourses) != 0)
    // style = model[i] % numberOfCourses;
    // else
    // style = numberOfCourses;
    //
    // if (style == 1) {
    // sequence.append("Ballett, ");
    // countBallet++;
    // } else if (style == 2) {
    // sequence.append("Rock, ");
    // countRock++;
    // } else if (style == 3) {
    // sequence.append("Klassik, ");
    // countClassic++;
    // } else if (style == 4) {
    // sequence.append("Hip Hop, ");
    // countHipHop++;
    // } else if (style == 5) {
    // sequence.append("Step, ");
    // countStep++;
    // }
    // }
    // }
    // System.out.println(sequence);
    // // verwende nur die Loesungen die die gewuenschte Anzahl an Stilen
    // // haben
    // // if (numberOfHipHop == countHipHop && numberOfRock == countRock
    // // && numberOfClassic == countClassic
    // // && numberOfBallet == countBallet
    // // && numberOfStep == countStep) {
    // // newModel.add(sequence.toString());
    // // System.out.println(sequence);
    // // }
    // }
    //
    // return newModel;
    // }

    // public void assignStyle(int[] model) {
    // ArrayList<String> newModel = new ArrayList<String>();
    // // zaehle mit wie oft ein Stil in einer Loesung vorkommt
    // int countHipHop = 0;
    // int countRock = 0;
    // int countClassic = 0;
    // int countBallet = 0;
    // int countStep = 0;
    //
    // // gehe die Liste mit allen Loesungen der Reihe nach durch
    //
    // StringBuffer sequence = new StringBuffer();
    //
    // // setze den Counter fuer die neue Loesung auf 0
    // countHipHop = countRock = countClassic = countBallet = countStep = 0;
    //
    // for (int i = 0; i < model.length; i++) {
    // int style;
    //
    // // verwende nur Variablen denen "true" zugewiesen wurde
    // if (model[i] > 0) {
    //
    // // mapping auf die Stile
    // if ((model[i] % numberOfCourses) != 0)
    // style = model[i] % numberOfCourses;
    // else
    // style = numberOfCourses;
    //
    // if (style == 1) {
    // sequence.append("Ballett, ");
    // countBallet++;
    // } else if (style == 2) {
    // sequence.append("Rock, ");
    // countRock++;
    // } else if (style == 3) {
    // sequence.append("Klassik, ");
    // countClassic++;
    // } else if (style == 4) {
    // sequence.append("Hip Hop, ");
    // countHipHop++;
    // } else if (style == 5) {
    // sequence.append("Step, ");
    // countStep++;
    // }
    // }
    // }
    // System.out.println(sequence);
    //
    // // verwende nur die Loesungen die die gewuenschte Anzahl an Stilen haben
    // // if (numberOfHipHop == countHipHop && numberOfRock == countRock &&
    // // numberOfClassic == countClassic && numberOfBallet == countBallet &&
    // // numberOfStep == countStep) {
    // // newModel.add(sequence.toString());
    // // System.out.println(sequence);
    // // }
    //
    // }

    ArrayList<int[]> executeSAT(String fileName) {
        solver = SolverFactory.newDefault();
        ModelIterator mi = new ModelIterator(solver);
        solver.setTimeout(600); // 10 min timeout
        Reader reader = new InstanceReader(mi);
        ArrayList<int[]> models = new ArrayList<int[]>();

        try {
            boolean unsat = true;
            IProblem problem = reader.parseInstance(fileName);

            int counter = 0;
            while (problem.isSatisfiable()) {
                if (counter == 3) {
                    break;
                }
                unsat = false;
                int[] model = problem.model();
                models.add(model);
                System.out.println(Arrays.toString(model));
                counter++;
            }
            if (unsat) {
                System.out.println("Unsatisfiable!");
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
        return models;
    }

    void executeSingleSAT(String fileName) {
        solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);

        try {
            IProblem problem = reader.parseInstance(fileName);
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                System.out.println(Arrays.toString(problem.model()));
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
    }

}
