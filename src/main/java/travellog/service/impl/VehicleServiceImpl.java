package travellog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;
import travellog.repository.TravelLogRepository;
import travellog.service.VehicleService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final TravelLogRepository travelLogRepository;
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
    public void delete(Long id) {
        try {
            travelLogRepository.delete(id);
        } catch (HttpClientErrorException.NotFound e) {
            e.getMessage();
        }
    }

    @Override
    public ReportResponse generateReportWithFilter(Optional<FilterDto> filterDto) {
        if(!filterDto.isEmpty()) {
            return travelLogRepository.generateReportWithFilter(filterDto);
        }else {
            return travelLogRepository.generateReport();

        }
    }

    @Override
    public List<VehicleDto> generateReport() {

        return travelLogRepository.generateReport().getVehicleList();
    }

}
