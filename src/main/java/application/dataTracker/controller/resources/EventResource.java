package application.dataTracker.controller.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventResource {
    private Date eventAt;
    private String eventType;
    private Long aggregatedNumericEventValue;
}
