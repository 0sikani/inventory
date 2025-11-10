package codeworld.projectjava.inventory.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // New endpoint with student relationships
    @GetMapping("/with-student")
    public ResponseEntity<List<Request>> getAllRequestsWithStudent() {
        List<Request> requests = reqService.getAllRequestsWithStudentRelations();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id){
        Optional<Request> req = reqService.findById(id);
        return req.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // New endpoint with student relationship
    @GetMapping("/{id}/with-student")
    public ResponseEntity<Request> getRequestWithStudent(@PathVariable Long id){
        Optional<Request> req = reqService.getRequestWithStudent(id);
        return req.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Request>> getRequestsByStudentId(@PathVariable Long studentId) {
        List<Request> requests = reqService.findByStudentId(studentId);
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id){
        reqService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}