package codeworld.projectjava.inventory.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        @RequestParam("studentName") String studentName,
        @RequestParam("birthDate") LocalDate birthDate,
        @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber) {
            Student student = new Student();
            student.setStudentName(studentName);
            student.setBirthDate(birthDate);  
            student.setEmail(email);
            student.setPhoneNumber(phoneNumber);
            
            return ResponseEntity.ok(stuService.createStudent(student));
        }

    @GetMapping
    public ResponseEntity<Stream<Student>> getAllStudents(){
        return ResponseEntity.ok(stuService.getAllStudents());
    }

    // New endpoint with relationships
    @GetMapping("/with-relations")
    public ResponseEntity<List<Student>> getAllStudentsWithRelations(){
        List<Student> students = stuService.getAllStudentsWithRelations();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Optional<Student> student = stuService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // New endpoint with relationships
    @GetMapping("/{id}/with-relations")
    public ResponseEntity<Student> getStudentWithRelations(@PathVariable Long id){
        Optional<Student> student = stuService.getStudentWithRelations(id);
        return student.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email){
        Optional<Student> student = stuService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        stuService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}