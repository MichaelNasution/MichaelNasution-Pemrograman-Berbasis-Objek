package academic.model;

public class StudentNotFoundException extends Exception {

    public StudentNotFoundException(String nimMahasiswa) {
        super("Mahasiswa dengan NIM " + nimMahasiswa + " tidak ditemukan");
    }
}
