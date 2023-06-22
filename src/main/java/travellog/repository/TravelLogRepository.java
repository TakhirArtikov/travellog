package travellog.repository;


import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.model.VehicleLog;

import java.util.List;

public interface TravelLogRepository {

    void save(VehicleLog vehicleLog);

    void edit(Long id, VehicleLog vehicleLog);

    void delete(Long id);

    List<VehicleLog> generateReport();

    ReportResponse generateReportWithFilter(FilterDto dto);

    VehicleLog findById(long id);

}
