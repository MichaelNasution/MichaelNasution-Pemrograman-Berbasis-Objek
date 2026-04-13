package academic.driver;

import academic.model.*;
import java.util.Scanner;
import java.util.TreeMap;

public class Driver1 {

    public static void main(String[] _args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String baris = scanner.nextLine();
            
            if (baris.equals("---")) {
                break;
            }
            
            String[] bagian = baris.split("#");
            String perintah = bagian[0];
            
            try {
                if (perintah.equals("course-add")) {
                    String kodeKuliah = bagian[1];
                    String namaMataKuliah = bagian[2];
                    int satuan = Integer.parseInt(bagian[3]);
                    char huruf = bagian[4].charAt(0);
                    
                    Course kuliah = new Course(kodeKuliah, namaMataKuliah, satuan, huruf);
                    CourseManager.tambahKuliah(kuliah);
                    
                } else if (perintah.equals("student-add")) {
                    String nimMahasiswa = bagian[1];
                    String namaMahasiswa = bagian[2];
                    int tahunMasuk = Integer.parseInt(bagian[3]);
                    String programStudi = bagian[4];
                    
                    Student mahasiswa = new Student(nimMahasiswa, namaMahasiswa, tahunMasuk, programStudi);
                    StudentManager.tambahMahasiswa(mahasiswa);
                    
                } else if (perintah.equals("enrollment-add")) {
                    String kodeKuliah = bagian[1];
                    String nimMahasiswa = bagian[2];
                    String tahunAkademik = bagian[3];
                    String semester = bagian[4];
                    
                    EnrollmentManager.tambahEnrollment(kodeKuliah, nimMahasiswa, tahunAkademik, semester);
                }
            } catch (DuplicateDataException e) {
                
            } catch (CourseNotFoundException | StudentNotFoundException e) {
                
            }
        }
        
        cetak();
        
        scanner.close();
    }

    private static void cetak() {
        TreeMap<String, Course> kuliah = CourseManager.semuaKuliah();
        for (Course k : kuliah.values()) {
            System.out.println(k);
        }
        
        TreeMap<String, Student> mahasiswa = StudentManager.semuaMahasiswa();
        for (Student m : mahasiswa.values()) {
            System.out.println(m);
        }
        
        TreeMap<String, Enrollment> enrollment = EnrollmentManager.semuaEnrollment();
        for (Enrollment e : enrollment.values()) {
            System.out.println(e);
        }
    }
}