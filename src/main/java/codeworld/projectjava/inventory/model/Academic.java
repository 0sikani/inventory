package codeworld.projectjava.inventory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Academic {
    private Long id;
    private String academicYear;
    private String programme;
    private String certificateType;
    private String otherDocs;
    private List<Student> students = new ArrayList<>();

    public Academic() {}

    public Academic(Long id, String academicYear, String programme,
                   String certificateType, String otherDocs) {
        this.id = id;
        this.academicYear = academicYear;
        this.programme = programme;
        this.certificateType = certificateType;
        this.otherDocs = otherDocs;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getAcademicYear() { return academicYear; }
    public String getProgramme() { return programme; }
    public String getCertificateType() { return certificateType; }
    public String getOtherDocs() { return otherDocs; }
    public List<Student> getStudents() { return students; }

    public void setId(Long id) { this.id = id; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setProgramme(String programme) { this.programme = programme; }
    public void setCertificateType(String certificateType) { this.certificateType = certificateType; }
    public void setOtherDocs(String otherDocs) { this.otherDocs = otherDocs; }
    public void setStudents(List<Student> students) { this.students = students; }

    // Helper method
    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Academic academic = (Academic) o;
        return Objects.equals(id, academic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}