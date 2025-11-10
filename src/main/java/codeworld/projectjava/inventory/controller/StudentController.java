package codeworld.projectjava.inventory.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codeworld.projectjava.inventory.model.Student;
import codeworld.projectjava.inventory.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService stuService;

    public StudentController(StudentService studentService){
        this.stuService = studentService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return ResponseEntity.ok(stuService.createStudent(student));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Student> createStudentForm(
        @RequestParam("academicId") Long academicId,
        @RequestParam("studentName") String studentName,
        @RequestParam("birthDate") LocalDate birDate,
        @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber) {
            Student student = new Student();
            student.setAcademicId(academicId);          
            student.setStudentName(studentName);
            student.setBirthDate(birDate);  
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            // student.setProfilePicturePath(profilePicturePath);
            
            return ResponseEntity.ok(stuService.createStudent(student));
        }



    @GetMapping
    public ResponseEntity<Stream<Student>> getAllStudents(){
        return ResponseEntity.ok(stuService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long id){
        Optional<Student> student = stuService.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<Student>> getStudentByEmail(@PathVariable String email){
        Optional<Student> student = stuService.getStudentByEmail(email);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        stuService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
