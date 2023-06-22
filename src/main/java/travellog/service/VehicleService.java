package travellog.service;


import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;

import java.util.List;


public interface VehicleService {

    void save(VehicleDto dto);

    void edit(Long id, VehicleDto dto);

    void delete(Long id);

    List<VehicleDto> generateReport();

    ReportResponse generateReportWithFilter(FilterDto dto);
}
