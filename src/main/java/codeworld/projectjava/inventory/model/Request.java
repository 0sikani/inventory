package codeworld.projectjava.inventory.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request {
    private Long id;
    private Long studentId;
    private Student student;
    private Long academicId;
    private Academic academic;
    private LocalDateTime requestDate;
    private String requestNature;
    private String sendingAddress;
    private String receivingAddress;
    private String requestStatus;

    public Request() {}

    public Request(Long id, Long studentId, Long academicId, LocalDateTime requestDate,
                   String requestNature, String sendingAddress, String receivingAddress,
                   String requestStatus) {
        this.id = id;
        this.studentId = studentId;
        this.academicId = academicId;
        this.requestDate = requestDate;
        this.requestNature = requestNature;
        this.sendingAddress = sendingAddress;
        this.receivingAddress = receivingAddress;
        this.requestStatus = requestStatus;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Student getStudent() { return student; }
    public Long getAcademicId() { return academicId; }
    public Academic getAcademic() { return academic; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public String getRequestNature() { return requestNature; }
    public String getSendingAddress() { return sendingAddress; }
    public String getReceivingAddress() { return receivingAddress; }
    public String getRequestStatus() { return requestStatus; }

    public void setId(Long id) { this.id = id; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setStudent(Student student) { this.student = student; }
    public void setAcademicId(Long academicId) { this.academicId = academicId; }
    public void setAcademic(Academic academic) { this.academic = academic; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public void setRequestNature(String requestNature) { this.requestNature = requestNature; }
    public void setSendingAddress(String sendingAddress) { this.sendingAddress = sendingAddress; }
    public void setReceivingAddress(String receivingAddress) { this.receivingAddress = receivingAddress; }
    public void setRequestStatus(String requestStatus) { this.requestStatus = requestStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}