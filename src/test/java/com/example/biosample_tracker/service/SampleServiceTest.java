package com.example.biosample_tracker.service;

import com.example.biosample_tracker.model.Sample;
import com.example.biosample_tracker.repository.SampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SampleServiceTest {

    @Mock
    private SampleRepository repository;

    @InjectMocks
    private SampleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_shouldReturnSample() {
        Sample sample = new Sample();
        sample.setId(1L);
        sample.setCode("CODE1");

        when(repository.findById(1L)).thenReturn(Optional.of(sample));

        Optional<Sample> result = service.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("CODE1");
        verify(repository).findById(1L);
    }


    @Test
    void testFindAll_shouldReturnList() {
        List<Sample> samples = List.of(new Sample(), new Sample());
        when(repository.findAll()).thenReturn(samples);

        List<Sample> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    void testSave_shouldReturnSavedSample() {
        Sample sample = new Sample();
        sample.setCode("CODE2");
        when(repository.save(sample)).thenReturn(sample);

        Sample result = service.save(sample);

        assertThat(result.getCode()).isEqualTo("CODE2");
        verify(repository).save(sample);
    }

    @Test
    void testDeleteById_shouldDeleteAndReturnTrue() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean result = service.deleteById(1L);

        assertThat(result).isTrue();
        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteById_shouldReturnFalseIfNotExists() {
        when(repository.existsById(2L)).thenReturn(false);

        boolean result = service.deleteById(2L);

        assertThat(result).isFalse();
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateSample_shouldUpdateAndReturnSample() {
        Sample existing = new Sample();
        existing.setId(1L);
        existing.setCode("OLD");
        existing.setDescription("desc");

        Sample updated = new Sample();
        updated.setCode("NEW");
        updated.setDescription("new desc");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Sample.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Sample> result = service.updateSample(1L, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo("NEW");
        assertThat(result.get().getDescription()).isEqualTo("new desc");
        verify(repository).findById(1L);
        verify(repository).save(existing);
    }

    @Test
    void testUpdateSample_shouldReturnEmptyIfNotFound() {
        Sample updated = new Sample();
        updated.setCode("NEW");

        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Sample> result = service.updateSample(99L, updated);

        assertThat(result).isEmpty();
        verify(repository).findById(99L);
        verify(repository, never()).save(any());
    }

}
