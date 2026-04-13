package academic.model;

public class Course {

    private final String kodeKuliah;
    private final String namaMataKuliah;
    private final int satuan;
    private final char huruf;

    public Course(String kodeKuliah, String namaMataKuliah, int satuan, char huruf) {
        this.kodeKuliah = kodeKuliah;
        this.namaMataKuliah = namaMataKuliah;
        this.satuan = satuan;
        this.huruf = huruf;
    }

    public String getKodeKuliah() {
        return kodeKuliah;
    }

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public int getSatuan() {
        return satuan;
    }

    public char getHuruf() {
        return huruf;
    }

    @Override
    public String toString() {
        return kodeKuliah + "|" + namaMataKuliah + "|" + satuan + "|" + huruf;
    }

}