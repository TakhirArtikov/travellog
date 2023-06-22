package travellog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.service.VehicleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/travel_log")
@AllArgsConstructor
public class VehicleLogController {

    private final VehicleService service;

    @Operation(summary = "Adding a new vehicle log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehicleDto.class))))
    })
    @PostMapping
    public ResponseEntity<String> create(@RequestBody VehicleDto dto) {

        try {
            service.save(dto);
            return new ResponseEntity<>("Vehicle log was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Editing vehicle log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehicleDto.class))))
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> edit(@PathVariable("id") long id, @RequestBody VehicleDto dto) {
        service.edit(id, dto);
        return new ResponseEntity<>("Vehicle log was updated successfully.", HttpStatus.OK);

    }

    @Operation(summary = "Deleting vehicle log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class))))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>("Vehicle log was deleted successfully.", HttpStatus.OK);

    }

    @Operation(summary = "Generate report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReportResponse.class))))
    })
    @PostMapping("/report")
    public ResponseEntity<?> generateReport(@RequestBody FilterDto filterDto) {

        if (filterDto != null) {
            try {
                List<ReportResponse> dtos = new ArrayList<>();
                dtos.add(service.generateReportWithFilter(filterDto));
                if (dtos.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(dtos, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(service.generateReport(), HttpStatus.OK);
        }
    }
}
