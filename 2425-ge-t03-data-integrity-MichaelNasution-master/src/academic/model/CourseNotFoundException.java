package academic.model;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException(String kodeKuliah) {
        super("Kuliah dengan kode " + kodeKuliah + " tidak ditemukan");
    }
}
