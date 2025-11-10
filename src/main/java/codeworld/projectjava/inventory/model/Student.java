package codeworld.projectjava.inventory.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private Long id;
    private String studentName;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private String profilePicturePath;
    private List<Academic> academics = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

    public Student() {}

    public Student(Long id, String studentName, LocalDate birthDate,
                  String email, String phoneNumber, String profilePicturePath) {
        this.id = id;
        this.studentName = studentName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePicturePath = profilePicturePath;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getStudentName() { return studentName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getProfilePicturePath() { return profilePicturePath; }
    public List<Academic> getAcademics() { return academics; }
    public List<Request> getRequests() { return requests; }

    public void setId(Long id) { this.id = id; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setProfilePicturePath(String profilePicturePath) { this.profilePicturePath = profilePicturePath; }
    public void setAcademics(List<Academic> academics) { this.academics = academics; }
    public void setRequests(List<Request> requests) { this.requests = requests; }

    // Helper methods
    public void addAcademic(Academic academic) {
        if (academic != null && !academics.contains(academic)) {
            academics.add(academic);
        }
    }

    public void addRequest(Request request) {
        if (request != null && !requests.contains(request)) {
            requests.add(request);
        }
    }
}