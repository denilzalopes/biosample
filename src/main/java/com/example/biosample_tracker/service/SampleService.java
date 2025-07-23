package com.example.biosample_tracker.service;

import com.example.biosample_tracker.model.Sample;
import com.example.biosample_tracker.repository.SampleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SampleService {
    private final SampleRepository repository;

    public SampleService(SampleRepository repository) {
        this.repository = repository;
    }

    public List<Sample> findAll() {
        return repository.findAll();
    }

    public Sample save(Sample sample) {
        return repository.save(sample);
    }

   public Optional<Sample> findById(Long id) {
       return repository.findById(id);
   }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Sample> updateSample(Long id, Sample updatedSample) {
        return repository.findById(id).map(existing -> {
            existing.setCode(updatedSample.getCode());
            existing.setDescription(updatedSample.getDescription());
            return repository.save(existing);
        });
    }
}