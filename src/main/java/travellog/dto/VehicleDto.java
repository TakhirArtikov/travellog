package travellog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private LocalDate date;
    private String vehicleNumber;
    private String vehicleOwner;
    private Long odometerStart;
    private Long odometerEnd;
    private String route;
    private String description;

    public VehicleDto(String vehicleNumber, String vehicleOwner, Long odometerStart, Long odometerEnd, String route, String description) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleOwner = vehicleOwner;
        this.odometerStart = odometerStart;
        this.odometerEnd = odometerEnd;
        this.route = route;
        this.description = description;
    }
}
