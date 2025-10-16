package codeworld.projectjava.inventory.model;

import java.time.LocalDateTime;
import java.util.List;

public class Request {

    private Long id;
    private Long studentId;
    private Long academicId;
    private LocalDateTime requestDate;
    private String requestNature;
    private String sendingAddress;
    private String receivingAddress;
    private String requestStatus;
    private List<Academic> academics; // Assuming Academic is another class in your project
    

    public Request() {}

    public Request(Long id, 
                   Long studentId, 
                   Long academicId, 
                   LocalDateTime requestDate, 
                   String requestNature, 
                   String sendingAddress, 
                   String receivingAddress, 
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


    public Long getId() {return id;}
    public Long getStudentId() {return studentId;}
    public Long getAcademicId() {return academicId;}
    public LocalDateTime getRequestDate() {return requestDate;}
    public String getRequestNature() {return requestNature;}        
    public String getSendingAddress() {return sendingAddress;}
    public String getReceivingAddress() {return receivingAddress;}  
    public String getRequestStatus() {return requestStatus;}
    public List<Academic> getAcademics(){ return academics; }

    public void setId(Long id) {this.id = id;}
    public void setStudentId(Long studentId) {this.studentId = studentId;}
    public void setAcademicId(Long academicId) {this.academicId = academicId;}
    public void setRequestDate(LocalDateTime requestDate){this.requestDate = requestDate; }
    public void setRequestNature(String requestNature) {this.requestNature = requestNature;}
    public void setSendingAddress(String sendingAddress) {this.sendingAddress = sendingAddress;}
    public void setReceivingAddress(String receivingAddress) {this.receivingAddress = receivingAddress;}
    public void setRequestStatus(String requestStatus) {this.requestStatus = requestStatus;}
    public void setAcademics(List<Academic> academics) { this.academics = academics; }
}
