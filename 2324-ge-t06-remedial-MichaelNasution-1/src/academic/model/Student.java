/**
 * @MichaelNasution 12S24003 - Michael Nasution
 */

package academic.model;

public class Student extends Person {
    private String year;
    private String major;

    public Student(String id, String name, String year, String major) {
        super(id, name);
        this.year = year;
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return super.getId() + "|" + super.getName() + "|" + this.year + "|" + this.major;
    }
}
