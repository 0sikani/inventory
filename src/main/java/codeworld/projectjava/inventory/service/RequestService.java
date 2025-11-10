package codeworld.projectjava.inventory.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import codeworld.projectjava.inventory.model.Request;
import codeworld.projectjava.inventory.repository.RequestRepository;

@Service
public class RequestService {
    private final RequestRepository reqRepo;

    public RequestService(RequestRepository requestRepository){
        this.reqRepo = requestRepository;
    }

    public Request createRequest(Request request){
        return reqRepo.save(request);
    }
    
    public Stream<Request> getAllRequest(){
        return reqRepo.findAll().stream();
    }

    public Optional<Request> findById(Long id){
        return reqRepo.findById(id);
    }

    public List<Request> findByStudentId(Long studentId) {
        return reqRepo.findByStudentId(studentId);
    }

    // New methods with relationships
    public List<Request> getAllRequestsWithStudentRelations() {
        return reqRepo.findAllWithStudentRelations();
    }

    public Optional<Request> getRequestWithStudent(Long id) {
        return reqRepo.findByIdWithStudent(id);
    }

    public void deleteRequest(Long id){
        reqRepo.deleteRequest(id);
    }
}