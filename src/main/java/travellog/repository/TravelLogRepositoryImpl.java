package travellog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import travellog.dto.FilterDto;
import travellog.model.VehicleLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TravelLogRepositoryImpl implements TravelLogRepository {

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
    public List<VehicleLog> generateReport() {
        String sql = "SELECT date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description " +
                        "FROM vehicle_log " +
                        "GROUP BY date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description " +
                        "ORDER BY odometer_start;";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(VehicleLog.class));

    }

    @Override
    public List<VehicleLog> generateReportWithFilter(Optional<FilterDto> dto) {


        String sql = "SELECT vehicle_log.date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description "
                + " FROM vehicle_log "
                + " WHERE vehicle_log.date BETWEEN '" + dto.get().getPeriodStart() + "' AND '" + dto.get().getPeriodEnd() + "'"
                + " AND  vehicle_number = '" + dto.get().getVehicleNumber()
                + "' AND vehicle_owner= '" + dto.get().getVehicleOwner()
                + "' GROUP BY vehicle_log.date, vehicle_number, vehicle_owner, odometer_start, odometer_end, route, description "
                + " ORDER BY odometer_start";


        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(VehicleLog.class));
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
