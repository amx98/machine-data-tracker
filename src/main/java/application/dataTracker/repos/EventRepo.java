package application.dataTracker.repos;

import application.dataTracker.repos.models.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.machineSessionId = ?1")
    List<Event> findByMachineSessionId(Long machineSessionId);

    @Query("SELECT e FROM Event e WHERE e.machineSessionId = ?1 and e.eventType = ?2")
    Event findEventBy(Long machineSessionId, String eventType );
}
