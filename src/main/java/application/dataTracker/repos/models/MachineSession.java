package application.dataTracker.repos.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "machine_session")
public class MachineSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 100)
    private String sessionId;
    @Column(nullable = false, length = 100)
    private String machineId;
    @Column(nullable = false)
    private Date startAt;
    @Column(nullable = false)
    private boolean isActive;


    public MachineSession(String sessionId, String machineId, Date startAt, boolean isActive) {
        this.sessionId = sessionId;
        this.machineId = machineId;
        this.startAt = startAt;
        this.isActive = isActive;
    }

}
