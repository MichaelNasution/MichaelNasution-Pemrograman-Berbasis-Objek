package academic.driver;

import academic.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class Driver1 {

    private static final Map<String, Student> students = new HashMap<>();
    private static final Map<String, Course> courses = new HashMap<>();
    private static final Map<String, String[]> allLecturerDetails = new LinkedHashMap<>();
    private static final Map<String, String> lecturerEmailsByInitial = new HashMap<>();
    private static final List<Enrollment> allEnrollments = new ArrayList<>();

    private static final Map<String, Double> GRADE_TO_GPA = new HashMap<>();
    static {
        GRADE_TO_GPA.put("A", 4.0);
        GRADE_TO_GPA.put("AB", 3.5);
        GRADE_TO_GPA.put("B", 3.0);
        GRADE_TO_GPA.put("BC", 2.5);
        GRADE_TO_GPA.put("C", 2.0);
        GRADE_TO_GPA.put("D", 1.0);
        GRADE_TO_GPA.put("E", 0.0);
        GRADE_TO_GPA.put("none", 0.0);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line;

        while (input.hasNextLine() && !(line = input.nextLine()).equals("---")) {
            String[] tokens = line.split("#");
            String command = tokens[0];

            switch (command) {
                case "student-add":
                    addStudent(tokens[1], tokens[2], tokens[3], tokens[4]);
                    break;
                case "course-add":
                    addCourse(tokens[1], tokens[2], Integer.parseInt(tokens[3]), tokens[4], tokens[5]);
                    break;
                case "enrollment-add":
                    addEnrollment(tokens[1], tokens[2], tokens[3], tokens[4]);
                    break;
                case "enrollment-grade":
                    setEnrollmentGrade(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    break;
                case "lecturer-add":
                    addLecturer(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    break;
                case "enrollment-remedial":
                    applyRemedial(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    break;
                case "student-details":
                    printStudentDetailsAndGPA(tokens[1]);
                    break;
            }
        }

        printLecturers();
        printCourses();
        printStudents();
        printAllEnrollments();

        input.close();
    }

    private static void addStudent(String id, String name, String year, String major) {
        students.putIfAbsent(id, new Student(id, name, year, major));
    }

    private static void addCourse(String code, String name, int credits, String gradeMin, String lecturerInitial) {
        courses.putIfAbsent(code, new Course(code, name, credits, gradeMin, lecturerInitial));
    }

    private static void addLecturer(String id, String name, String initial, String email, String department) {
        if (!allLecturerDetails.containsKey(id)) {
            allLecturerDetails.put(id, new String[]{id, name, initial, email, department});
            lecturerEmailsByInitial.put(initial, email);
        }
    }

    private static void addEnrollment(String courseCode, String studentId, String year, String semester) {
        Course c = courses.get(courseCode);
        Student s = students.get(studentId);

        if (c != null && s != null) {
            Enrollment e = new Enrollment(c, s, year, semester, "none");
            if (!allEnrollments.contains(e)) {
                allEnrollments.add(e);
            }
        }
    }

    // ✅ FINAL FIX
    private static void setEnrollmentGrade(String courseCode, String studentId, String year, String semester, String grade) {
        for (Enrollment e : allEnrollments) {
            if (e.getCourse().getCourseCode().equals(courseCode) &&
                e.getStudent().getId().equals(studentId) &&
                e.getYear().equals(year) &&
                e.getSemester().equals(semester)) {

                e.setGrade(grade);
                return;
            }
        }
    }

    private static void applyRemedial(String courseCode, String studentId, String year, String semester, String newGrade) {
        for (Enrollment e : allEnrollments) {
            if (e.getCourse().getCourseCode().equals(courseCode) &&
                e.getStudent().getId().equals(studentId) &&
                e.getYear().equals(year) &&
                e.getSemester().equals(semester)) {

                e.applyRemedialGrade(newGrade);
                return;
            }
        }
    }

    private static List<Enrollment> getLatestEnrollments(Student student) {
        Map<String, Enrollment> latest = new HashMap<>();

        List<Enrollment> list = allEnrollments.stream()
            .filter(e -> e.getStudent().equals(student))
            .collect(Collectors.toList());

        list.sort((a, b) -> {
            int y1 = Integer.parseInt(a.getYear().split("/")[0]);
            int y2 = Integer.parseInt(b.getYear().split("/")[0]);
            if (y1 != y2) return Integer.compare(y1, y2);
            return a.getSemester().compareTo(b.getSemester());
        });

        for (Enrollment e : list) {
            latest.put(e.getCourse().getCourseCode(), e);
        }

        return new ArrayList<>(latest.values());
    }

    private static double[] calculateGPAAndCredits(Student s) {
        List<Enrollment> list = getLatestEnrollments(s);
        double total = 0;
        int credits = 0;

        for (Enrollment e : list) {
            if (!e.getGrade().equals("none")) {
                double g = GRADE_TO_GPA.get(e.getGrade());
                total += g * e.getCourse().getCredits();
                credits += e.getCourse().getCredits();
            }
        }

        return new double[]{
            credits == 0 ? 0 : total / credits,
            credits
        };
    }

    private static void printStudentDetailsAndGPA(String id) {
        Student s = students.get(id);
        if (s != null) {
            double[] res = calculateGPAAndCredits(s);
            System.out.println(
                s.getId() + "|" + s.getName() + "|" + s.getYear() + "|" + s.getMajor() +
                "|" + String.format(Locale.US, "%.2f", res[0]) + "|" + (int) res[1]
            );
        }
    }

    private static void printLecturers() {
        List<String[]> list = new ArrayList<>(allLecturerDetails.values());
        list.sort((a, b) -> a[0].compareTo(b[0]));
        for (String[] l : list) {
            System.out.println(String.join("|", l));
        }
    }

    private static void printCourses() {
        List<Course> list = new ArrayList<>(courses.values());
        list.sort((a, b) -> a.getCourseCode().compareTo(b.getCourseCode()));

        for (Course c : list) {
            String[] initials = c.getLecturerInitial().split(",");
            List<String> parts = new ArrayList<>();

            for (String i : initials) {
                if (lecturerEmailsByInitial.containsKey(i)) {
                    parts.add(i + " (" + lecturerEmailsByInitial.get(i) + ")");
                }
            }

            System.out.println(c + "|" + String.join(";", parts));
        }
    }

    private static void printStudents() {
        students.values().stream()
            .sorted((a, b) -> a.getId().compareTo(b.getId()))
            .forEach(System.out::println);
    }

    private static void printAllEnrollments() {
        allEnrollments.stream()
            .sorted((a, b) -> {
                int s = a.getStudent().getId().compareTo(b.getStudent().getId());
                if (s != 0) return s;
                int c = a.getCourse().getCourseCode().compareTo(b.getCourse().getCourseCode());
                if (c != 0) return c;
                int y1 = Integer.parseInt(a.getYear().split("/")[0]);
                int y2 = Integer.parseInt(b.getYear().split("/")[0]);
                if (y1 != y2) return Integer.compare(y1, y2);
                return a.getSemester().compareTo(b.getSemester());
            })
            .forEach(System.out::println);
    }
}