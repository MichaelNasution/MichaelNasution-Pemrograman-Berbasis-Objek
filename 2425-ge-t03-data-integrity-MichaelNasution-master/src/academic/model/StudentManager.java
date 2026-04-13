package academic.model;

import java.util.TreeMap;

public class StudentManager {

    private static final TreeMap<String, Student> daftarMahasiswa = new TreeMap<>();

    public static void tambahMahasiswa(Student mahasiswa) throws DuplicateDataException {
        if (daftarMahasiswa.containsKey(mahasiswa.getNimMahasiswa())) {
            throw new DuplicateDataException("Mahasiswa dengan NIM " + mahasiswa.getNimMahasiswa() + " sudah ada");
        }
        daftarMahasiswa.put(mahasiswa.getNimMahasiswa(), mahasiswa);
    }

    public static Student ambilMahasiswa(String nimMahasiswa) throws StudentNotFoundException {
        Student mahasiswa = daftarMahasiswa.get(nimMahasiswa);
        if (mahasiswa == null) {
            throw new StudentNotFoundException(nimMahasiswa);
        }
        return mahasiswa;
    }

    public static boolean mahasiswaAda(String nimMahasiswa) {
        return daftarMahasiswa.containsKey(nimMahasiswa);
    }

    public static TreeMap<String, Student> semuaMahasiswa() {
        return new TreeMap<>(daftarMahasiswa);
    }

    public static void bersihkan() {
        daftarMahasiswa.clear();
    }
}
