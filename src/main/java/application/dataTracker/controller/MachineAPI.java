package application.dataTracker.controller;

import application.dataTracker.controller.resources.EventResource;

import java.util.List;

public interface MachineAPI {

    List<EventResource> getMachineEventsBy(String machineId, String sessionId);

    String getMachineBy(String machineId);

    List<String> getAllMachines();
}
