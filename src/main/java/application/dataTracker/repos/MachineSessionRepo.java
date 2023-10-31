package application.dataTracker.repos;

import application.dataTracker.repos.models.MachineSession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineSessionRepo extends CrudRepository<MachineSession, Long> {

    @Modifying
    @Query("update MachineSession m set m.isActive = false where m.machineId = ?1")
    void disableExistingSessionsFor(String machineId);

    @Query("SELECT m.id FROM MachineSession m WHERE m.machineId = ?1 and m.sessionId = ?2 and m.isActive = true")
    Long findActiveMachineSessionIdBy(String machineId, String sessionId);

    @Query("SELECT m.id FROM MachineSession m WHERE m.sessionId = ?1 and m.isActive = true")
    Long findActiveMachineSessionIdBy(String sessionId);

    @Query("SELECT distinct m.machineId FROM MachineSession m")
    List<String> findAllMachineIds();

    @Query("SELECT distinct m.sessionId FROM MachineSession m WHERE m.isActive = true")
    String findMostRecentSessionIdBy(String machineId);


}
