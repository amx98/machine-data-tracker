package application.dataTracker.services;

import application.dataTracker.repos.EventRepo;
import application.dataTracker.repos.MachineSessionRepo;
import application.dataTracker.repos.models.Event;
import application.dataTracker.repos.models.MachineSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MachineServiceTest {

    @Mock
    private MachineSessionRepo machineSessionRepo;

    @Mock
    private EventRepo eventRepo;

    private MachineService machineService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        machineService = new MachineService();

        injectPrivateField(machineService, "machineSessionRepo", machineSessionRepo);
        injectPrivateField(machineService, "eventRepo", eventRepo);
    }

    @Test
    public void addMachineSessionAndExpectSaving() {
        String sessionId = "sessionId";
        String machineId = "machineId";
        Date startAt = new Date();

        doNothing().when(machineSessionRepo).disableExistingSessionsFor(machineId);
        when(machineSessionRepo.save(any(MachineSession.class))).thenReturn(new MachineSession());

        machineService.addMachineSession(sessionId, machineId, startAt);

        verify(machineSessionRepo, times(1)).disableExistingSessionsFor(machineId);
        verify(machineSessionRepo, times(1)).save(any(MachineSession.class));
    }

    @Test
    public void saveEventForExistingSessionExpectRepoMethodsCalled() {
        String sessionId = "sessionId";
        Date eventAt = new Date();
        String eventType = "eventType";
        Long numericEventValue = 123L;
        Long activeMachineSessionId = 1L;

        when(machineSessionRepo.findActiveMachineSessionIdBy(sessionId)).thenReturn(activeMachineSessionId);
        when(eventRepo.findEventBy(activeMachineSessionId, eventType)).thenReturn(null);
        when(eventRepo.save(any(Event.class))).thenReturn(new Event());

        machineService.addSessionEvent(sessionId, eventAt, eventType, numericEventValue);

        verify(machineSessionRepo, times(1)).findActiveMachineSessionIdBy(sessionId);
        verify(eventRepo, times(1)).findEventBy(activeMachineSessionId, eventType);
        verify(eventRepo, times(1)).save(any(Event.class));
    }

    @Test
    public void findExistingMachineEventAndExpectRepoMethodsCalled() {
        String machineId = "machineId";
        String sessionId = "sessionId";
        Long machineSessionId = 1L;

        when(machineSessionRepo.findActiveMachineSessionIdBy(machineId, sessionId)).thenReturn(machineSessionId);
        when(eventRepo.findByMachineSessionId(machineSessionId)).thenReturn(Collections.singletonList(new Event()));

        List<Event> events = machineService.findMachineEventsBy(machineId, sessionId);

        verify(machineSessionRepo, times(1)).findActiveMachineSessionIdBy(machineId, sessionId);
        verify(eventRepo, times(1)).findByMachineSessionId(machineSessionId);
        assertEquals(1, events.size());
    }

    @Test
    public void findMostRecentMachineAndExpectRepoMethodCalled() {
        String machineId = "machineId";
        when(machineSessionRepo.findMostRecentSessionIdBy(machineId)).thenReturn("mostRecentSessionId");

        String mostRecentSession = machineService.findMostRecentMachineSessionBy(machineId);

        verify(machineSessionRepo, times(1)).findMostRecentSessionIdBy(machineId);
        assertEquals("mostRecentSessionId", mostRecentSession);
    }

    @Test
    public void findAllMachineIdsAndExpectRepoMethodCalled() {
        when(machineSessionRepo.findAllMachineIds()).thenReturn(Collections.singletonList("machineId"));

        List<String> machineIds = machineService.findAllMachineIds();

        verify(machineSessionRepo, times(1)).findAllMachineIds();
        assertEquals(1, machineIds.size());
    }


    private void injectPrivateField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting private field: " + fieldName, e);
        }
    }
}