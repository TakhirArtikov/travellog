package travellog.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class FilterDto {

    private String vehicleNumber;
    private String vehicleOwner;
    private LocalDate periodStart;
    private LocalDate periodEnd;
}
