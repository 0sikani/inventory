package codeworld.projectjava.inventory.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import codeworld.projectjava.inventory.model.Academic;
import codeworld.projectjava.inventory.service.AcademicService;

@RestController
@RequestMapping("/api/academic")
public class AcademicController {
    private final AcademicService acaService;

    public AcademicController(AcademicService academicService){
        this.acaService = academicService;
    }
    
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Academic> createAcademicJson(@RequestBody Academic academic){
        return ResponseEntity.ok(acaService.createAcademic(academic));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Academic> createAcademicForm(
            @RequestParam("academicYear") String academicYear,
            @RequestParam("programme") String programme,
            @RequestParam("certificateType") String certificateType,
            @RequestParam("otherDocs") String otherDocs) {
        
        Academic academic = new Academic();
        academic.setAcademicYear(academicYear);
        academic.setProgramme(programme);
        academic.setCertificateType(certificateType);
        academic.setOtherDocs(otherDocs);
        
        return ResponseEntity.ok(acaService.createAcademic(academic));
    }

    @GetMapping
    public ResponseEntity<Stream<Academic>> getAllAcademic(){
        return ResponseEntity.ok(acaService.getAllAcademic());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Academic>> getAcademicsByStudentId(@PathVariable Long studentId) {
        List<Academic> academics = acaService.getAcademicsByStudentId(studentId);
        return ResponseEntity.ok(academics);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Academic> getAcademicById(@PathVariable Long id){
        Optional<Academic> academic = acaService.getAcademicById(id);
        return academic.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademic(@PathVariable Long id){
        acaService.deleteAcademic(id);
        return ResponseEntity.noContent().build();
    }
}