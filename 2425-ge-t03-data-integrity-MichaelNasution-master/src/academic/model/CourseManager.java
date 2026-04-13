package academic.model;

import java.util.TreeMap;

public class CourseManager {

    private static final TreeMap<String, Course> daftarKuliah = new TreeMap<>();

    public static void tambahKuliah(Course kuliah) throws DuplicateDataException {
        if (daftarKuliah.containsKey(kuliah.getKodeKuliah())) {
            throw new DuplicateDataException("Kuliah dengan kode " + kuliah.getKodeKuliah() + " sudah ada");
        }
        daftarKuliah.put(kuliah.getKodeKuliah(), kuliah);
    }

    public static Course ambilKuliah(String kodeKuliah) throws CourseNotFoundException {
        Course kuliah = daftarKuliah.get(kodeKuliah);
        if (kuliah == null) {
            throw new CourseNotFoundException(kodeKuliah);
        }
        return kuliah;
    }

    public static boolean kuliahAda(String kodeKuliah) {
        return daftarKuliah.containsKey(kodeKuliah);
    }

    public static TreeMap<String, Course> semuaKuliah() {
        return new TreeMap<>(daftarKuliah);
    }

    public static void bersihkan() {
        daftarKuliah.clear();
    }
}
