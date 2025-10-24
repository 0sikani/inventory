package codeworld.projectjava.inventory.controller;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request req){
        return ResponseEntity.ok(reqService.createRequest(req));
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
