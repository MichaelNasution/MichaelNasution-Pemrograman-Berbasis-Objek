/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.model;




public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private String gradeMinimum;
    private String lecturer; // As per current problem context, lecturer is a String in Course

    public Course(String courseCode, String courseName, int credits, String gradeMinimum, String lecturer) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.gradeMinimum = gradeMinimum;
        this.lecturer = lecturer;
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

    public String getLecturer() {
        return lecturer;
    }

    @Override
    public String toString() {
        return this.courseCode + "|" + this.courseName + "|" + this.credits + "|" + this.gradeMinimum + "|" + this.lecturer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        if (courseCode == null) {
            if (other.courseCode != null) return false;
        } else if (!courseCode.equals(other.courseCode)) return false;
        return true;
    }
}
