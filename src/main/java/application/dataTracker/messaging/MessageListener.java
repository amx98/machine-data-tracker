package application.dataTracker.messaging;

import application.dataTracker.messaging.models.Session;
import application.dataTracker.messaging.models.SessionEvents;
import application.dataTracker.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class MessageListener {

    @Autowired
    MachineService machineService;

    @RabbitListener(queues = "session-queue")
    public void receiveMessage(Session session) {
        System.out.println("Received session: " + session);
        machineService.addMachineSession(session.getSessionId(), session.getMachineId(), session.getStartAt());
    }

    @RabbitListener(queues = "session-events-queue")
    public void receiveMessage(SessionEvents sessionEvents) {
        System.out.println("Received session events: " + sessionEvents);
        sessionEvents.getEvents().forEach(event -> {
            machineService.addSessionEvent(sessionEvents.getSessionId(), event.getEventAt(), event.getEventType(), event.getNumericEventValue());
        });
    }
}
