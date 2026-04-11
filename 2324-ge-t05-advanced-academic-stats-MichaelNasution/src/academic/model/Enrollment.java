/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.model;

import java.util.Objects;

public class Enrollment {
    private Course course;
    private Student student;
    private String year; // e.g., "2023/2024"
    private String semester; // e.g., "odd", "even"
    private String grade; // e.g., "A", "AB", "B", "BC", "C", "D", "E"

    public Enrollment(Course course, Student student, String year, String semester, String grade) {
        this.course = course;
        this.student = student;
        this.year = year;
        this.semester = semester;
        this.grade = (grade == null || grade.isEmpty()) ? "none" : grade; // Default grade if not set
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Compares two enrollments to determine which one is "later" based on year and semester.
     * @param other The other Enrollment to compare with.
     * @return true if this enrollment is later than the other, false otherwise.
     */
    public boolean isLaterThan(Enrollment other) {
        // Parse years: "YYYY/YYYY" -> YYYY for comparison
        int thisStartYear = Integer.parseInt(this.year.split("/")[0]);
        int otherStartYear = Integer.parseInt(other.year.split("/")[0]);

        if (thisStartYear > otherStartYear) {
            return true;
        } else if (thisStartYear < otherStartYear) {
            return false;
        } else { // Same start year, compare semesters
            // "even" is considered later than "odd"
            if (this.semester.equals("even") && other.semester.equals("odd")) {
                return true;
            } else if (this.semester.equals("odd") && other.semester.equals("even")) {
                return false;
            }
            // If semesters are also the same (e.g., both "odd" or both "even"),
            // we can consider them equal in terms of time, or use other criteria if available.
            // For this problem, if year and semester are identical, either one could be "last"
            // unless there's an explicit ordering. Sticking to the problem's rule.
            return false; // Not strictly later if years and semesters are same or earlier
        }
    }

    @Override
    public String toString() {
        return student.getId() + "|" + course.getCourseCode() + "|" + year + "|" + semester + "|" + grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return course.equals(that.course) &&
               student.equals(that.student) &&
               year.equals(that.year) &&
               semester.equals(that.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, student, year, semester);
    }
}
