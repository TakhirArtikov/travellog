package travellog.repository;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import travellog.dto.FilterDto;
import travellog.dto.ReportResponse;
import travellog.dto.VehicleDto;
import travellog.mapper.VehicleMapper;
import travellog.model.VehicleLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcVehicleRepository implements TravelLogRepository {

    private final VehicleMapper vehicleMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(VehicleLog vehicleLog) {
        jdbcTemplate.update("INSERT into vehicle_log (date,vehicle_number,vehicle_owner," +
                        "odometer_start,odometer_end,route, description) VALUES(?,?,?,?,?,?,?)",
                LocalDateTime.now(), vehicleLog.getVehicleNumber(), vehicleLog.getVehicleOwner(),
                vehicleLog.getOdometerStart(), vehicleLog.getOdometerEnd(), vehicleLog.getRoute(),
                vehicleLog.getDescription());
    }

    @Override
    public void edit(Long id, VehicleLog vehicleLog) {
        jdbcTemplate.update("UPDATE vehicle_log SET date=?, vehicle_number=?,vehicle_owner=?,odometer_start=?," +
                        "odometer_end=?,route=?,description=? WHERE id=?",
                vehicleLog.getDate(), vehicleLog.getVehicleNumber(), vehicleLog.getVehicleOwner(),
                vehicleLog.getOdometerStart(), vehicleLog.getOdometerEnd(), vehicleLog.getRoute(),
                vehicleLog.getDescription(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM vehicle_log WHERE id=?", id);
    }

    @Override
    public ReportResponse generateReport() {
        String sql = "SELECT date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description " +
                        "FROM vehicle_log " +
                        "GROUP BY date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description " +
                        "ORDER BY odometer_start;";

        List<VehicleLog> vehicleLogs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(VehicleLog.class));
        List<VehicleDto> vehicleDtos = vehicleLogs.stream()
                .map(vehicleMapper::toDto)
                .toList();
        ReportResponse response = new ReportResponse();
        response.setVehicleList(vehicleDtos);
        response.setTotalDistance(null);

        return response;
    }

    @Override
    public ReportResponse generateReportWithFilter(Optional<FilterDto> dto) {


        String sql = "SELECT vehicle_log.date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description "
                + " FROM vehicle_log "
                + " WHERE vehicle_log.date BETWEEN '" + dto.get().getPeriodStart() + "' AND '" + dto.get().getPeriodEnd() + "'"
                + " AND  vehicle_number = '" + dto.get().getVehicleNumber()
                + "' AND vehicle_owner= '" + dto.get().getVehicleOwner()
                + "' GROUP BY vehicle_log.date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description "
                + " ORDER BY odometer_start";


        List<VehicleLog> vehicleLogs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(VehicleLog.class));
        List<VehicleDto> vehicleDtos = vehicleLogs.stream()
                .map(vehicleMapper::toDto)
                .toList();
        Long totalDistance = vehicleDtos.stream()
                .map(v -> v.getOdometerEnd() - v.getOdometerStart())
                .reduce(0L, Long::sum);
        ReportResponse response = new ReportResponse();
        response.setVehicleList(vehicleDtos);
        response.setTotalDistance(totalDistance);

        return response;
    }

    @Override
    public VehicleLog findById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM vehicle_log WHERE id=?",
                    BeanPropertyRowMapper.newInstance(VehicleLog.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


}
