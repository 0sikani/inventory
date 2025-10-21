package codeworld.projectjava.inventory.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import codeworld.projectjava.inventory.model.Academic;
import codeworld.projectjava.inventory.repository.AcademicRepository;

@Service
public class AcademicService {
    private final AcademicRepository acaRepo;

    public AcademicService(AcademicRepository academicRepository){
        this.acaRepo = academicRepository;
    }

    public Academic createAcademic(Academic academic){
        Academic savedAcademic = acaRepo.save(academic);
        return savedAcademic;
    }

    public Stream<Academic> getAllAcademic(){
        return acaRepo.findAll().stream();
    }

    public Optional<Academic> getAcademicById(Long id){
        return acaRepo.findById(id);
    }

    public Optional<Academic> getAcademicByEmail(String email){
        return acaRepo.findByEmail(email);
    }

    public void deleteAcademic(Long id){
        acaRepo.delete(id);
    }
}
