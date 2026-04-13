package academic.model;

public class Enrollment {

    private final String kodeKuliah;
    private final String nimMahasiswa;
    private final String tahunAkademik;
    private final String semester;
    private String nilaiAkhir;

    public Enrollment(String kodeKuliah, String nimMahasiswa, String tahunAkademik, String semester) {
        this.kodeKuliah = kodeKuliah;
        this.nimMahasiswa = nimMahasiswa;
        this.tahunAkademik = tahunAkademik;
        this.semester = semester;
        this.nilaiAkhir = "None";
    }

    public String getKodeKuliah() {
        return kodeKuliah;
    }

    public String getNimMahasiswa() {
        return nimMahasiswa;
    }

    public String getTahunAkademik() {
        return tahunAkademik;
    }

    public String getSemester() {
        return semester;
    }

    public String getNilaiAkhir() {
        return nilaiAkhir;
    }

    public void setNilaiAkhir(String nilaiAkhir) {
        this.nilaiAkhir = nilaiAkhir;
    }

    @Override
    public String toString() {
        return kodeKuliah + "|" + nimMahasiswa + "|" + tahunAkademik + "|" + semester + "|" + nilaiAkhir;
    }

}