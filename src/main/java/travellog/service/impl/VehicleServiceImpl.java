package travellog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;
import travellog.repository.TravelLogRepository;
import travellog.repository.reactive.VehicleReactiveRepository;
import travellog.service.VehicleService;


import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final TravelLogRepository travelLogRepository;
    private final VehicleReactiveRepository vehicleReactiveRepository;
    private final VehicleMapper mapper;

    @Override
    public void save(VehicleDto dto) {
        VehicleLog log = mapper.toEntity(dto);
        travelLogRepository.save(log);
    }

    @Override
    public void edit(Long id, VehicleDto dto) {
        VehicleLog log = travelLogRepository.findById(id);
        if (log != null) {
            travelLogRepository.edit(id, mapper.toEntity(dto));
        } else {
            throw new NoSuchElementException("Vehicle log not found under id:" + id);
        }
    }

    @Override
    public Mono<Void> delete(Long id) {
        return vehicleReactiveRepository.deleteById(id);
    }

    @Override
    public ReportResponse generateReportWithFilter(Optional<FilterDto> filterDto) throws IOException {
        ReportResponse response = new ReportResponse();

        if(filterDto.isPresent()) {
            List<VehicleDto> vehicleDtos = travelLogRepository.generateReportWithFilter(filterDto).stream()
                    .map(mapper::toDto)
                    .toList();
            Long totalDistance = vehicleDtos.stream()
                    .map(v -> v.getOdometerEnd() - v.getOdometerStart())
                    .reduce(0L, Long::sum);
            response.setVehicleList(vehicleDtos);
            response.setTotalDistance(totalDistance);
            return response;
        }else {
          List<VehicleDto> vehicleDtos = travelLogRepository.generateReport().stream()
                    .map(mapper::toDto)
                    .toList();
            response.setVehicleList(vehicleDtos);
            response.setTotalDistance(null);
            return response;

        }
    }
}
