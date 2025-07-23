package com.example.biosample_tracker.repository;
import com.example.biosample_tracker.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SampleRepository extends JpaRepository<Sample, Long> {}