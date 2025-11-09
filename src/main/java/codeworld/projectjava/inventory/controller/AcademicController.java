package codeworld.projectjava.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import codeworld.projectjava.inventory.model.Academic;
import codeworld.projectjava.inventory.service.AcademicService;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/academic")
public class AcademicController {
    private final AcademicService acaService;

    public AcademicController(AcademicService academicService){
        this.acaService = academicService;
    }
    
    // Handle JSON requests
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Academic> createAcademicJson(@RequestBody Academic academic){
        return ResponseEntity.ok(acaService.createAcademic(academic));
    }

    // Handle form data (URL encoded)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Academic> createAcademicForm(
            @RequestParam("academicYear") String academicYear,
            @RequestParam("programme") String programme,
            @RequestParam("cirtificateType") String cirtificateType,
            @RequestParam("otherDocs") String otherDocs) {
        
        Academic academic = new Academic();
        academic.setAcademicYear(academicYear);
        academic.setProgramme(programme);
        academic.setCirtificateType(cirtificateType);
        academic.setOtherDocs(otherDocs);
        
        return ResponseEntity.ok(acaService.createAcademic(academic));
    }

    @GetMapping
    public ResponseEntity<Stream<Academic>> getAllAcademic(){
        return ResponseEntity.ok(acaService.getAllAcademic());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Academic>> getAcademicById(@PathVariable Long id){
        Optional<Academic> academic = acaService.getAcademicById(id);
        return academic.isPresent() ? ResponseEntity.ok(academic) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Academic>> getAcademicByEmail(@PathVariable String email){
        Optional<Academic> academic = acaService.getAcademicByEmail(email);
        return academic.isPresent() ? ResponseEntity.ok(academic) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademic(@PathVariable Long id){
        acaService.deleteAcademic(id);
        return ResponseEntity.noContent().build();
    }
}