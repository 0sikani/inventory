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
    
    @PostMapping
    public ResponseEntity<Academic> createAcadenic(@RequestBody Academic academic){
        return ResponseEntity.ok(acaService.createAcademic(academic));
    }

    @GetMapping
    public ResponseEntity<Stream<Academic>> getAllAcademic(){
        return ResponseEntity.ok(acaService.getAllAcademic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Academic>> getAcademicById(@PathVariable Long id){
        Optional<Academic> academic = acaService.getAcademicById(id);
        return academic != null ? ResponseEntity.ok(academic) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<Academic>> getAcademicByEmail(@PathVariable String email){
        Optional<Academic> academic = acaService.getAcademicByEmail(email);
        return academic != null ? ResponseEntity.ok(academic) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteAcademic(@PathVariable Long id){
            acaService.deleteAcademic(id);
            return ResponseEntity.noContent().build();
        }
}
