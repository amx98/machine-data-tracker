package application.dataTracker.services;

import application.dataTracker.repos.EventRepo;
import application.dataTracker.repos.MachineSessionRepo;
import application.dataTracker.repos.models.Event;
import application.dataTracker.repos.models.MachineSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MachineService {

    @Autowired
    private MachineSessionRepo machineSessionRepo;

    @Autowired
    private EventRepo eventRepo;


    public void addMachineSession(String sessionId, String machineId, Date startAt) {
        machineSessionRepo.disableExistingSessionsFor(machineId);
        MachineSession newSession = new MachineSession(sessionId, machineId, startAt, true);
        machineSessionRepo.save(newSession);
    }

    public void addSessionEvent(String sessionId, Date eventAt, String eventType, Long numericEventValue) {
        Long activeMachineSessionId = machineSessionRepo.findActiveMachineSessionIdBy(sessionId);

        if (activeMachineSessionId != null) {
            Event existingEvent = activeMachineSessionId == null ? null : eventRepo.findEventBy(activeMachineSessionId, eventType);

            if (existingEvent == null || !existingEvent.getEventType().equals(eventType)) {
                Event newEvent = new Event(activeMachineSessionId, eventAt, eventType, numericEventValue);
                eventRepo.save(newEvent);
            } else {
                Long aggregatedValue = Long.sum(existingEvent.getAggregatedNumericEventValue(), numericEventValue);
                existingEvent.setAggregatedNumericEventValue(aggregatedValue);
                eventRepo.save(existingEvent);
            }
        } else {
            System.out.println("No active machine session found for sessionId: " + sessionId);
        }
    }


    public List<Event> findMachineEventsBy(String machineId, String sessionId) {
        Long machineSession = machineSessionRepo.findActiveMachineSessionIdBy(machineId, sessionId);
        return eventRepo.findByMachineSessionId(machineSession);
    }


    public String findMostRecentMachineSessionBy(String machineId) {
        return machineSessionRepo.findMostRecentSessionIdBy(machineId);
    }

    public List<String> findAllMachineIds() {
        return machineSessionRepo.findAllMachineIds();
    }


}
