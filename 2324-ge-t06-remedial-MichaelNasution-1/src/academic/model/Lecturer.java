package academic.model;



public class Lecturer extends Person {
    private String initial;
    private String email;
    private String department;

    public Lecturer(String id, String name, String initial, String email, String department) {
        super(id, name);
        this.initial = initial;
        this.email = email;
        this.department = department;
    }

    public String getInitial() {
        return initial;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return super.getId() + "|" + super.getName() + "|" + this.initial + "|" + this.email + "|" + this.department;
    }
}
