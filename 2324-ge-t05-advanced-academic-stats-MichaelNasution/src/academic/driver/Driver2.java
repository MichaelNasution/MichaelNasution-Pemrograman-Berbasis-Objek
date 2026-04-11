/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.driver;

import academic.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class Driver2 {

    private static final Map<String, Student> students = new HashMap<>();
    private static final Map<String, Course> courses = new HashMap<>();
    private static final Map<String, Lecturer> lecturers = new HashMap<>(); // New: To store lecturers
    private static final List<Enrollment> allEnrollments = new ArrayList<>();

    // Grade to GPA mapping
    private static final Map<String, Double> GRADE_TO_GPA = new HashMap<>();
    static {
        GRADE_TO_GPA.put("A", 4.0);
        GRADE_TO_GPA.put("AB", 3.5);
        GRADE_TO_GPA.put("B", 3.0);
        GRADE_TO_GPA.put("BC", 2.5);
        GRADE_TO_GPA.put("C", 2.0);
        GRADE_TO_GPA.put("D", 1.0);
        GRADE_TO_GPA.put("E", 0.0);
        GRADE_TO_GPA.put("none", 0.0); // Assuming 'none' grade counts as 0 for GPA
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line;

        while (!(line = input.nextLine()).equals("---")) {
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
                case "lecturer-add": // New Command
                    addLecturer(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    break;
                case "enrollment-remedial": // New Command
                    applyRemedial(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    break;
                case "student-details": // New Command
                    printStudentDetailsAndGPA(tokens[1]); // Prints student details + GPA immediately
                    break;
                default:
                    // Optionally handle unknown commands or ignore
                    break;
            }
        }

        // Final output as requested
        printStudents();
        printCourses();
        printLecturers(); // Print lecturers (new)
        printAllEnrollments();
        printStudentGPA();

        input.close();
    }

    private static void addStudent(String id, String name, String year, String major) {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name, year, major));
        }
    }

    private static void addCourse(String code, String name, int credits, String gradeMin, String lecturerInitial) {
        if (!courses.containsKey(code)) {
            courses.put(code, new Course(code, name, credits, gradeMin, lecturerInitial));
        }
    }

    private static void addLecturer(String id, String name, String initial, String email, String department) {
        if (!lecturers.containsKey(id)) {
            lecturers.put(id, new Lecturer(id, name, initial, email, department));
        }
    }

    private static void addEnrollment(String courseCode, String studentId, String year, String semester) {
        Course course = courses.get(courseCode);
        Student student = students.get(studentId);

        if (course != null && student != null) {
            Enrollment newEnrollment = new Enrollment(course, student, year, semester, "none");
            // Check for duplicates before adding (same course, student, year, semester)
            if (!allEnrollments.contains(newEnrollment)) {
                allEnrollments.add(newEnrollment);
            }
        }
    }

    private static void setEnrollmentGrade(String courseCode, String studentId, String year, String semester, String grade) {
        for (Enrollment e : allEnrollments) {
            if (e.getCourse().getCourseCode().equals(courseCode) &&
                e.getStudent().getId().equals(studentId) &&
                e.getYear().equals(year) &&
                e.getSemester().equals(semester)) {
                // Only set grade if it's not a remedial already
                // For existing grade, enrollment-grade effectively just updates it if no remedial was done
                // If remedial was done, this command should not overwrite it.
                // Based on requirements, enrollment-grade is for initial grade or regular updates.
                // Remedial is a separate command.
                if (!e.isRemedialAttempted()) {
                    e.setGrade(grade);
                }
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
        Map<String, Enrollment> latestEnrollmentsPerCourse = new HashMap<>();

        List<Enrollment> studentEnrollments = allEnrollments.stream()
            .filter(e -> e.getStudent().equals(student))
            .collect(Collectors.toList());

        // Sort student enrollments chronologically to ensure correct "latest" pick
        // Sorting by Year (earliest first), then Semester (odd before even)
        studentEnrollments.sort((e1, e2) -> {
            int year1 = Integer.parseInt(e1.getYear().split("/")[0]);
            int year2 = Integer.parseInt(e2.getYear().split("/")[0]);
            if (year1 != year2) return Integer.compare(year1, year2);
            // "odd" comes before "even" lexicographically, which matches "odd" before "even" chronologically
            return e1.getSemester().compareTo(e2.getSemester());
        });

        for (Enrollment currentEnrollment : studentEnrollments) {
            String courseCode = currentEnrollment.getCourse().getCourseCode();
            
            // The logic from isLaterThan is actually applied in the sequential processing of sorted list.
            // When iterating a chronologically sorted list, the last one encountered for a course is the latest.
            latestEnrollmentsPerCourse.put(courseCode, currentEnrollment);
        }
        return new ArrayList<>(latestEnrollmentsPerCourse.values());
    }

    private static String calculateGPA(Student student) {
        List<Enrollment> latestEnrollments = getLatestEnrollments(student);
        double totalWeightedGrade = 0;
        int totalCredits = 0;

        for (Enrollment e : latestEnrollments) {
            // Only consider enrollments with a valid grade for GPA calculation (not "none")
            if (!e.getGrade().equals("none")) {
                Double gpaValue = GRADE_TO_GPA.get(e.getGrade());
                if (gpaValue != null) {
                    totalWeightedGrade += gpaValue * e.getCourse().getCredits();
                    totalCredits += e.getCourse().getCredits();
                }
            }
        }

        if (totalCredits == 0) {
            return String.format(Locale.US, "%.2f", 0.00); // No credits, GPA is 0.00
        } else {
            return String.format(Locale.US, "%.2f", totalWeightedGrade / totalCredits);
        }
    }

    // --- Printing methods ---

    private static void printStudents() {
        List<Student> sortedStudents = students.values().stream()
                .sorted((s1, s2) -> s1.getId().compareTo(s2.getId()))
                .collect(Collectors.toList());
        for (Student student : sortedStudents) {
            System.out.println(student);
        }
    }

    private static void printCourses() {
        List<Course> sortedCourses = courses.values().stream()
                .sorted((c1, c2) -> c1.getCourseCode().compareTo(c2.getCourseCode()))
                .collect(Collectors.toList());
        for (Course course : sortedCourses) {
            System.out.println(course);
        }
    }
    
    private static void printLecturers() {
        List<Lecturer> sortedLecturers = lecturers.values().stream()
                .sorted((l1, l2) -> l1.getId().compareTo(l2.getId()))
                .collect(Collectors.toList());
        for (Lecturer lecturer : sortedLecturers) {
            System.out.println(lecturer);
        }
    }

    private static void printAllEnrollments() {
        List<Enrollment> sortedEnrollments = allEnrollments.stream()
                .sorted((e1, e2) -> {
                    int studentCompare = e1.getStudent().getId().compareTo(e2.getStudent().getId());
                    if (studentCompare != 0) return studentCompare;
                    int courseCompare = e1.getCourse().getCourseCode().compareTo(e2.getCourse().getCourseCode());
                    if (courseCompare != 0) return courseCompare;
                    
                    // Chronological sort for consistency in output
                    int year1 = Integer.parseInt(e1.getYear().split("/")[0]);
                    int year2 = Integer.parseInt(e2.getYear().split("/")[0]);
                    if (year1 != year2) return Integer.compare(year1, year2);
                    return e1.getSemester().compareTo(e2.getSemester()); // "odd" < "even"
                })
                .collect(Collectors.toList());
        for (Enrollment enrollment : sortedEnrollments) {
            System.out.println(enrollment);
        }
    }

    private static void printStudentGPA() {
        List<Student> sortedStudents = students.values().stream()
                .sorted((s1, s2) -> s1.getId().compareTo(s2.getId()))
                .collect(Collectors.toList());
        for (Student student : sortedStudents) {
            String gpa = calculateGPA(student);
            System.out.println(student.getId() + "|" + gpa);
        }
    }

    // New: Method to handle student-details command (prints immediately upon command)
    private static void printStudentDetailsAndGPA(String studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            String gpa = calculateGPA(student);
            System.out.println(student.getId() + "|" + student.getName() + "|" + student.getYear() + "|" + student.getMajor() + "|" + gpa);
        }
    }
}
