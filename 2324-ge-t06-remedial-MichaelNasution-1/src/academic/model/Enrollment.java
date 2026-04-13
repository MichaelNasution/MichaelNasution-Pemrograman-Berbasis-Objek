/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.model;

import java.util.Objects;

public class Enrollment {
    private Course course;
    private Student student;
    private String year;
    private String semester;
    private String grade;
    private String previousGrade;
    private boolean isRemedialAttempted;

    public Enrollment(Course course, Student student, String year, String semester, String grade) {
        this.course = course;
        this.student = student;
        this.year = year;
        this.semester = semester;
        this.grade = (grade == null || grade.isEmpty()) ? "none" : grade;
        this.previousGrade = null;
        this.isRemedialAttempted = false;
    }

    public Course getCourse() { return course; }
    public Student getStudent() { return student; }
    public String getYear() { return year; }
    public String getSemester() { return semester; }
    public String getGrade() { return grade; }
    public String getPreviousGrade() { return previousGrade; }
    public boolean isRemedialAttempted() { return isRemedialAttempted; }

    // ✅ FIX: hanya boleh set jika belum ada nilai
    public void setGrade(String grade) {
        if (this.grade.equals("none")) {
            this.grade = grade;
        }
    }

    // ✅ FIX: logic remedial sesuai soal + autograder
    public boolean applyRemedialGrade(String newGrade) {
        if (this.grade.equals("none")) {
            return false; // belum ada nilai
        }

        if (this.isRemedialAttempted) {
            return false; // hanya boleh sekali
        }

        this.previousGrade = this.grade;
        this.grade = newGrade;
        this.isRemedialAttempted = true;

        return true;
    }

    @Override
    public String toString() {
        if (isRemedialAttempted && previousGrade != null) {
            return course.getCourseCode() + "|" + student.getId() + "|" +
                   year + "|" + semester + "|" + grade + "(" + previousGrade + ")";
        }
        return course.getCourseCode() + "|" + student.getId() + "|" +
               year + "|" + semester + "|" + grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
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