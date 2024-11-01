package CarmineGargiulo.Progetto_Settimana_18.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingDTO(
        String preferences,
        @NotNull(message = "Employee id must be provided")
        UUID employeeId,
        @NotNull(message = "Trip id must be provided")
        UUID tripId) {

}
