package codeworld.projectjava.inventory.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import codeworld.projectjava.inventory.model.Request;
import codeworld.projectjava.inventory.repository.RequestRepository;

@Service
public class RequestService {
    private RequestRepository reqRepo;

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

    public Optional<Request> findByEmail(String email){
        return reqRepo.findByEmail(email);
    }

    public void deleteRequest(Long id){
        reqRepo.deleteRequest(id);
    }
}
