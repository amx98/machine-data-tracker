package application.dataTracker.repos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 100)
    private Long machineSessionId;
    @Column(nullable = false)
    private Date eventAt;
    @Column(nullable = false, length = 100)
    private String eventType;

    @Setter
    @Column(nullable = false)
    private Long aggregatedNumericEventValue;

    public Event(Long machineSessionId, Date eventAt, String eventType, Long aggregatedNumericEventValue) {
        this.machineSessionId = machineSessionId;
        this.eventAt = eventAt;
        this.eventType = eventType;
        this.aggregatedNumericEventValue = aggregatedNumericEventValue;
    }
}
