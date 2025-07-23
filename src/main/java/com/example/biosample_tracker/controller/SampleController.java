package com.example.biosample_tracker.controller;

import com.example.biosample_tracker.model.Sample;
import com.example.biosample_tracker.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
public class SampleController {
    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sample> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sample> getById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sample create(@Valid @RequestBody Sample sample) {
        return service.save(sample);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sample> update(@PathVariable Long id, @Valid @RequestBody Sample updatedSample) {
        return service.updateSample(id, updatedSample)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}