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
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;
import travellog.repository.TravelLogRepository;
import travellog.repository.reactive.VehicleReactiveRepository;
import travellog.service.impl.VehicleServiceImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Mock
    private VehicleReactiveRepository vehicleReactiveRepository;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());



   VehicleDto dto = objectMapper.readValue(new File("./src/test/resources/data.json"),
                new TypeReference<>() {
    });

    ServiceImplTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(vehicleService, "travelLogRepository", travelLogRepository);
        ReflectionTestUtils.setField(vehicleService, "mapper", mapper);
        ReflectionTestUtils.setField(vehicleService,"vehicleReactiveRepository",vehicleReactiveRepository);
    }


    @Test
    void testSave() {

        VehicleServiceImpl vehicleService = mock(VehicleServiceImpl.class);
        vehicleService.save(dto);
        verify(vehicleService, times(1)).save(dto);
    }

    @Test
    void testEdit() {
        Long id = 1L;

        VehicleServiceImpl vehicleService = mock(VehicleServiceImpl.class);
        vehicleService.edit(id, dto);
        verify(vehicleService, times(1)).edit(id, dto);

    }


    @Test
    void deleteTest() {

        Long id = 1L;

        vehicleService.delete(id);

        verify(vehicleReactiveRepository, times(1)).deleteById(id);

    }

    @Test
    void testReport() throws IOException {
        List<VehicleLog> listVehicleLog = objectMapper.readValue(new File("./src/test/resources/vehicleLog.json"), new TypeReference<>() {});
        when(travelLogRepository.generateReport()).thenReturn(listVehicleLog);

        when(mapper.toDto(any(VehicleLog.class))).thenAnswer((Answer<VehicleDto>) invocation -> {
            VehicleLog vehicleLog = invocation.getArgument(0);
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleNumber(vehicleLog.getVehicleNumber());
            vehicleDto.setVehicleOwner(vehicleLog.getVehicleOwner());
            vehicleDto.setDate(vehicleLog.getDate());
            vehicleDto.setOdometerStart(vehicleLog.getOdometerStart());
            vehicleDto.setOdometerEnd(vehicleLog.getOdometerEnd());
            vehicleDto.setRoute(vehicleLog.getRoute());
            vehicleDto.setDescription(vehicleLog.getDescription());
            return vehicleDto;
        });
        ReportResponse actualReportResponse = vehicleService.generateReportWithFilter(Optional.empty());

        ReportResponse  expectedReportResponse = objectMapper.readValue(new File("./src/test/resources/responseUnfiltered.json"), ReportResponse.class);

        assertEquals(expectedReportResponse, actualReportResponse);

    }

    @Test
    void testReportWithFilter() throws IOException {
        FilterDto dto= objectMapper.readValue(new File("./src/test/resources/filterDto.json"), FilterDto.class);
        List<VehicleLog>  vehicleLogList = objectMapper.readValue(new File("./src/test/resources/filteredDto.json"), new TypeReference<>() {});
        when(travelLogRepository.generateReportWithFilter(Optional.ofNullable(dto))).thenReturn(vehicleLogList);
        when(mapper.toDto(any(VehicleLog.class))).thenAnswer((Answer<VehicleDto>) invocation -> {
            VehicleLog vehicleLog = invocation.getArgument(0);
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setVehicleNumber(vehicleLog.getVehicleNumber());
            vehicleDto.setVehicleOwner(vehicleLog.getVehicleOwner());
            vehicleDto.setDate(vehicleLog.getDate());
            vehicleDto.setOdometerStart(vehicleLog.getOdometerStart());
            vehicleDto.setOdometerEnd(vehicleLog.getOdometerEnd());
            vehicleDto.setRoute(vehicleLog.getRoute());
            vehicleDto.setDescription(vehicleLog.getDescription());
            return vehicleDto;
        });
        ReportResponse actualResponse = vehicleService
                .generateReportWithFilter(Optional.ofNullable(dto));

        ReportResponse expectedResponse= objectMapper.readValue(new File("./src/test/resources/responseFiltered.json"),ReportResponse.class);

        assertEquals(expectedResponse, actualResponse);

    }

}
