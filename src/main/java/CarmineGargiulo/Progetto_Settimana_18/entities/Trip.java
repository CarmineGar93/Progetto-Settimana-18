package CarmineGargiulo.Progetto_Settimana_18.entities;

import CarmineGargiulo.Progetto_Settimana_18.enums.TripState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue
    @Column(name = "trip_id")
    @Setter(AccessLevel.NONE)
    private UUID tripId;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TripState state;

    public Trip(String destination, LocalDate date) {
        this.destination = destination;
        this.date = date;
        this.state = TripState.IN_PROGRAM;
    }


}
