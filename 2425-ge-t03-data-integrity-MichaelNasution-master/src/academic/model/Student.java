package academic.model;

public class Student {

    private final String nimMahasiswa;
    private final String namaMahasiswa;
    private final int tahunMasuk;
    private final String programStudi;

    public Student(String nimMahasiswa, String namaMahasiswa, int tahunMasuk, String programStudi) {
        this.nimMahasiswa = nimMahasiswa;
        this.namaMahasiswa = namaMahasiswa;
        this.tahunMasuk = tahunMasuk;
        this.programStudi = programStudi;
    }

    public String getNimMahasiswa() {
        return nimMahasiswa;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public int getTahunMasuk() {
        return tahunMasuk;
    }

    public String getProgramStudi() {
        return programStudi;
    }

    @Override
    public String toString() {
        return nimMahasiswa + "|" + namaMahasiswa + "|" + tahunMasuk + "|" + programStudi;
    }

}