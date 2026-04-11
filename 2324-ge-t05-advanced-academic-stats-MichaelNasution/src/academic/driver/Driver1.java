/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.driver;

import academic.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Driver1 {

    private static final Map<String, Student> students = new HashMap<>();
    private static final Map<String, Course> courses = new HashMap<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();

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
                default:
                    // Optionally handle unknown commands or ignore
                    break;
            }
        }

        // Output data as requested
        printStudents();
        printCourses();
        printEnrollments();

        input.close();
    }

    private static void addStudent(String id, String name, String year, String major) {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name, year, major));
        }
    }

    private static void addCourse(String code, String name, int credits, String gradeMin, String lecturer) {
        if (!courses.containsKey(code)) {
            courses.put(code, new Course(code, name, credits, gradeMin, lecturer));
        }
    }

    private static void addEnrollment(String courseCode, String studentId, String year, String semester) {
        Course course = courses.get(courseCode);
        Student student = students.get(studentId);

        if (course != null && student != null) {
            Enrollment newEnrollment = new Enrollment(course, student, year, semester, "none"); // Default grade "none"
            enrollments.add(newEnrollment);
        }
    }

    private static void setEnrollmentGrade(String courseCode, String studentId, String year, String semester, String grade) {
        for (Enrollment e : enrollments) {
            if (e.getCourse().getCourseCode().equals(courseCode) &&
                e.getStudent().getId().equals(studentId) &&
                e.getYear().equals(year) &&
                e.getSemester().equals(semester)) {
                e.setGrade(grade);
                return;
            }
        }
    }

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

    private static void printEnrollments() {
        // No specific sorting mentioned for enrollment, print as added or sorted by student/course
        // Sort by student ID, then course code, then year, then semester for consistent output
        List<Enrollment> sortedEnrollments = enrollments.stream()
                .sorted((e1, e2) -> {
                    int studentCompare = e1.getStudent().getId().compareTo(e2.getStudent().getId());
                    if (studentCompare != 0) return studentCompare;
                    int courseCompare = e1.getCourse().getCourseCode().compareTo(e2.getCourse().getCourseCode());
                    if (courseCompare != 0) return courseCompare;
                    int yearCompare = e1.getYear().compareTo(e2.getYear());
                    if (yearCompare != 0) return yearCompare;
                    return e1.getSemester().compareTo(e2.getSemester());
                })
                .collect(Collectors.toList());
        for (Enrollment enrollment : sortedEnrollments) {
            System.out.println(enrollment);
        }
    }
}
