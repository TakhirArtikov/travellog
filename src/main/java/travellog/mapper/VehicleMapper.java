package travellog.mapper;

import org.springframework.stereotype.Component;
import travellog.dto.VehicleDto;
import travellog.model.VehicleLog;

@Component
public class VehicleMapper {

    public VehicleDto toDto(VehicleLog log) {
        VehicleDto dto = new VehicleDto();

        dto.setDate(log.getDate());
        dto.setVehicleNumber(log.getVehicleNumber());
        dto.setVehicleOwner(log.getVehicleOwner());
        dto.setOdometerStart(log.getOdometerStart());
        dto.setOdometerEnd(log.getOdometerEnd());
        dto.setRoute(log.getRoute());
        dto.setDescription(log.getDescription());

        return dto;
    }

    public VehicleLog toEntity(VehicleDto dto) {
        VehicleLog log = new VehicleLog();

        log.setDate(dto.getDate());
        log.setVehicleNumber(dto.getVehicleNumber());
        log.setVehicleOwner(dto.getVehicleOwner());
        log.setOdometerStart(dto.getOdometerStart());
        log.setOdometerEnd(dto.getOdometerEnd());
        log.setRoute(dto.getRoute());
        log.setDescription(dto.getDescription());
        return log;
    }
}
