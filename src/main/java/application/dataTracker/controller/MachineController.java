package application.dataTracker.controller;

import application.dataTracker.controller.resources.EventResource;
import application.dataTracker.repos.models.Event;
import application.dataTracker.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = {"machines"})
public class MachineController implements MachineAPI {

    @Autowired
    MachineService machineService;


    @GetMapping("/{machineId}/sessions/{sessionId}/events")
    public List<EventResource> getMachineEventsBy(@PathVariable String machineId, @PathVariable String sessionId) {
        List<Event> events = machineService.findMachineEventsBy(machineId, sessionId);
        if (!CollectionUtils.isEmpty(events)) {
            return events.stream().map(event ->
                    new EventResource(event.getEventAt(), event.getEventType(), event.getAggregatedNumericEventValue())
            ).toList();
        }
        return Collections.emptyList();
    }


    @GetMapping("/{machineId}/sessions/recentSession")
    public String getMachineBy(@PathVariable String machineId) {
        return machineService.findMostRecentMachineSessionBy(machineId);
    }

    @GetMapping("/")
    public List<String> getAllMachines() {
        return machineService.findAllMachineIds();
    }

}
