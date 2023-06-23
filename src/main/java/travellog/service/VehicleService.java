package travellog.service;


import org.springframework.http.ResponseEntity;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;

import java.util.List;
import java.util.Optional;


public interface VehicleService {

    void save(VehicleDto dto);

    void edit(Long id, VehicleDto dto);

    void delete(Long id);

    List<VehicleDto> generateReport();

    ReportResponse generateReportWithFilter(Optional<FilterDto> dto);
}
