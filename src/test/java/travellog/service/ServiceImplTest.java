package travellog.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;
import travellog.repository.TravelLogRepository;
import travellog.service.impl.VehicleServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ServiceImplTest {

    @Mock
    private TravelLogRepository travelLogRepository;

    @Mock
    private VehicleMapper mapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(vehicleService, "travelLogRepository", travelLogRepository);
        ReflectionTestUtils.setField(vehicleService, "mapper", mapper);
    }


    @Test
    void testSave() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        VehicleDto dto = objectMapper.readValue(new File("./src/test/resources/data.json"),
                new TypeReference<>() {
                });
        VehicleServiceImpl vehicleService = mock(VehicleServiceImpl.class);
        vehicleService.save(dto);
        verify(vehicleService, times(1)).save(dto);
    }

    @Test
    void testEdit() throws IOException {
        Long id = 1L;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        VehicleDto dto = objectMapper.readValue(new File("./src/test/resources/data.json"),
                new TypeReference<>() {
                });
        VehicleServiceImpl vehicleService = mock(VehicleServiceImpl.class);
        vehicleService.edit(id, dto);
        verify(vehicleService, times(1)).edit(id, dto);

    }


    @Test
    void deleteTest() {

        Long id = 1L;

        vehicleService.delete(id);

        verify(travelLogRepository, times(1)).delete(id);

    }

    @Test
    void testReport() throws IOException {

        when(travelLogRepository.generateReport()).thenReturn(new ReportResponse());

        ReportResponse reportResponse = vehicleService
                .generateReportWithFilter(Optional.empty());

        assertEquals(new ReportResponse(), reportResponse);

    }

}
