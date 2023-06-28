package travellog.repository;


import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.model.VehicleLog;

import java.util.List;
import java.util.Optional;

public interface TravelLogRepository {

    void save(VehicleLog vehicleLog);

    void edit(Long id, VehicleLog vehicleLog);

    void delete(Long id);

    List<VehicleLog> generateReport();

    List<VehicleLog> generateReportWithFilter(Optional<FilterDto> dto);

    VehicleLog findById(long id);

}
