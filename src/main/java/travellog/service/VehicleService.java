package travellog.service;



import reactor.core.publisher.Mono;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface VehicleService {

    void save(VehicleDto dto);

    void edit(Long id, VehicleDto dto);

    Mono<Void> delete(Long id);

    ReportResponse generateReportWithFilter(Optional<FilterDto> dto) throws IOException;
}
