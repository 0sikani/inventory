package codeworld.projectjava.inventory.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codeworld.projectjava.inventory.model.Request;
import codeworld.projectjava.inventory.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService reqService;

    public RequestController(RequestService requestService){
        this.reqService = requestService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Request> createRequest(@RequestBody Request req){
        return ResponseEntity.ok(reqService.createRequest(req));
    }

    
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Request> createRequestForm(
            @RequestParam("studentId") Long studentId,
            @RequestParam("academicId") Long academicId,
            @RequestParam("requestDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime requestDate,
            @RequestParam("requestNature") String requestNature,
            @RequestParam("sendingAddress") String sendingAddress,
            @RequestParam("receivingAddress") String receivingAddress,
            @RequestParam("requestStatus") String requestStatus) {
        
        Request request = new Request();
        request.setStudentId(studentId);
        request.setAcademicId(academicId);
        request.setRequestDate(requestDate);
        request.setRequestNature(requestNature);
        request.setSendingAddress(sendingAddress);
        request.setReceivingAddress(receivingAddress);
        request.setRequestStatus(requestStatus);
        
        return ResponseEntity.ok(reqService.createRequest(request));
    }


    @GetMapping
    public ResponseEntity<Stream<Request>> getAllRequest(){
        return ResponseEntity.ok(reqService.getAllRequest());
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Optional<Request>> getRequestById(@PathVariable Long id){
        Optional<Request> req = reqService.findById(id);
        return req != null ? ResponseEntity.ok(req) : ResponseEntity.notFound().build();
    }

    @GetMapping("/api/{email}")
    public ResponseEntity<Optional<Request>> getRequestByEmail(@PathVariable String email){
        Optional<Request> req = reqService.findByEmail(email);
        return req != null ? ResponseEntity.ok(req) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id){
        reqService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}


    