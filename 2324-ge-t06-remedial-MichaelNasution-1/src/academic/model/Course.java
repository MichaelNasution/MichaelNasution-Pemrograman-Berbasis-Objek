/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.model;

import java.util.Objects;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private String gradeMinimum;
    private String lecturerInitial; 

    public Course(String courseCode, String courseName, int credits, String gradeMinimum, String lecturerInitial) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.gradeMinimum = gradeMinimum;
        this.lecturerInitial = lecturerInitial;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getGradeMinimum() {
        return gradeMinimum;
    }

    public String getLecturerInitial() {
        return lecturerInitial;
    }

    @Override
    public String toString() {
        // Driver1 akan menangani penambahan (email) di printCourses()
        return this.courseCode + "|" + this.courseName + "|" + this.credits + "|" + this.gradeMinimum + "|" + this.lecturerInitial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        return Objects.equals(courseCode, other.courseCode);
    }
}
