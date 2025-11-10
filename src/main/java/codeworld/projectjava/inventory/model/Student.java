package codeworld.projectjava.inventory.model;

import java.time.LocalDate;
import java.util.List;

public class Student {
    
   private Long id;
   private Long academicId;
   private String studentName;
   private LocalDate birthDate;
   private String email;
   private String phoneNumber;
   private String profilePicturePath;
   private List<Academic> academics; // Assuming Academic is another class in your project
   private List<Request> requests; // Assuming Request is another class in your project

   public Student() {}

   public Student(Long id,
                  Long academicId,
                  String studentName,
                  LocalDate birthDate,
                  String email,
                  String phoneNumber,
                  String profilePicturePath) {

      this.id = id;
      this.academicId = academicId;
      this.studentName = studentName;
      this.birthDate = birthDate;
      this.email = email;
      this.phoneNumber = phoneNumber;
      this.profilePicturePath = profilePicturePath;
   }

   public Long getId() { return id; }
   public Long getAcademicId() { return academicId; }
   public String getStudentName() { return studentName; }
   public LocalDate getBirthDate() { return birthDate; }
   public String getEmail() { return email; }
   public String getPhoneNumber() { return phoneNumber; }
   // public String getProfilePicturePath() { return profilePicturePath; }
   public List<Academic> getAcademics(){ return academics; }
   public List<Request> getRequests() { return requests; }

   public void setId(Long id) { this.id = id; }
   public void setAcademicId(Long academicId) { this.academicId = academicId; }
   public void setStudentName(String studentName) { this.studentName = studentName; }
   public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
   public void setEmail(String email) { this.email = email; }
   public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
   // public void setProfilePicturePath(String profilePicturePath) { this.profilePicturePath = profilePicturePath; }
   public void setAcademics(List<Academic> academics) { this.academics = academics; }
   public void setRequests(List<Request> requests) { this.requests = requests; }
}



