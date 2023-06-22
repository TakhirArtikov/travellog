package travellog.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReportResponse {
    private List<VehicleDto> vehicleList;
    private Long totalDistance;
}
