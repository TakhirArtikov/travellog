package travellog.repository.reactive;


import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import travellog.model.VehicleLog;

@Repository
public interface VehicleReactiveRepository extends ReactiveCrudRepository<VehicleLog,Long> {

    @Query("delete from vehicle_log where id=:id")
    Mono<Void> deleteById(@Param("id")Long id);

}
