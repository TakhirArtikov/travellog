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
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;
import travellog.repository.TravelLogRepository;
import travellog.service.impl.VehicleServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ServiceImplTest {

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
        VehicleLog model1 = new VehicleLog("ABC123", "John Doe", 2000L, 3000L, "Route 1", "Desc 1"); // Sample data
        VehicleLog model2 = new VehicleLog("DEF456", "Jane Smith", 4000L, 5000L, "Route 2", "Desc 2"); // Sample data

        List<VehicleLog> mockList = new ArrayList<>();
        mockList.add(model1);
        mockList.add(model2);

        when(travelLogRepository.generateReport()).thenReturn(mockList);

        VehicleDto dto1 = new VehicleDto("ABC123", "John Doe", 2000L, 3000L, "Route 1", "Desc 1"); // Expected DTO
        VehicleDto dto2 = new VehicleDto("DEF456", "Jane Smith", 4000L, 5000L, "Route 2", "Desc 2"); // Expected DTO

        List<VehicleDto> expectedList = new ArrayList<>();
        expectedList.add(dto1);
        expectedList.add(dto2);

        when(mapper.toDto(model1)).thenReturn(dto1);
        when(mapper.toDto(model2)).thenReturn(dto2);

        List<VehicleDto> actualList = vehicleService.generateReport();

        assertEquals(expectedList, actualList);
    }

}
