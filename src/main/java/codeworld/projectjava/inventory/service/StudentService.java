package codeworld.projectjava.inventory.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import codeworld.projectjava.inventory.model.Student;
import codeworld.projectjava.inventory.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo){
        this.studentRepo = studentRepo;
    }
    
    public Student createStudent(Student student){
        Student savedStudent = studentRepo.save(student);
        return savedStudent;
    }

    public Optional<Student> getStudentById(Long id){
        return studentRepo.findById(id);
    }

    public Optional<Student> getStudentByEmail(String email){
        return studentRepo.findByEmail(email);
    }

    public Stream<Student> getAllStudents(){
        return studentRepo.findAll().stream();
    }

    // New methods with relationships
    public List<Student> getAllStudentsWithRelations() {
        return studentRepo.findAllWithRelations();
    }

    public Optional<Student> getStudentWithRelations(Long id) {
        return studentRepo.findByIdWithRelations(id);
    }

    public void deleteStudent(Long id){
        studentRepo.delete(id);
    }
}