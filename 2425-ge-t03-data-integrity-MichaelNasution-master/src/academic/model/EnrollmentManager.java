package academic.model;

import java.util.TreeMap;

public class EnrollmentManager {

    private static final TreeMap<String, Enrollment> daftarEnrollment = new TreeMap<>();

    public static Enrollment tambahEnrollment(String kodeKuliah, String nimMahasiswa, 
                                          String tahunAkademik, String semester)
            throws CourseNotFoundException, StudentNotFoundException, DuplicateDataException {
        
        if (!CourseManager.kuliahAda(kodeKuliah)) {
            throw new CourseNotFoundException(kodeKuliah);
        }
        
        if (!StudentManager.mahasiswaAda(nimMahasiswa)) {
            throw new StudentNotFoundException(nimMahasiswa);
        }
        
        String kunci = kodeKuliah + "-" + nimMahasiswa + "-" + tahunAkademik + "-" + semester;
        if (daftarEnrollment.containsKey(kunci)) {
            throw new DuplicateDataException("Enrollment sudah ada: " + kunci) ;
        }
        
        Enrollment enrollment = new Enrollment(kodeKuliah, nimMahasiswa, tahunAkademik, semester);
        daftarEnrollment.put(kunci, enrollment);
        
        return enrollment;
    }

    public static Enrollment ambilEnrollment(String kodeKuliah, String nimMahasiswa,
                                          String tahunAkademik, String semester)
            throws StudentNotFoundException {
        String kunci = kodeKuliah + "-" + nimMahasiswa + "-" + tahunAkademik + "-" + semester;
        Enrollment enrollment = daftarEnrollment.get(kunci);
        if (enrollment == null) {
            throw new StudentNotFoundException("Enrollment tidak ditemukan");
        }
        return enrollment;
    }

    public static TreeMap<String, Enrollment> semuaEnrollment() {
        return new TreeMap<>(daftarEnrollment);
    }

    public static void bersihkan() {
        daftarEnrollment.clear();
    }
}
