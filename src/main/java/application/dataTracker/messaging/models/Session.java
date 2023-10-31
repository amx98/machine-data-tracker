package application.dataTracker.messaging.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Session {
    private String sessionId;
    private String machineId;
    private Date startAt;
}
