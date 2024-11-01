package CarmineGargiulo.Progetto_Settimana_18.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record BookingDTO(
        String preferences,
        @NotEmpty(message = "Employee id must be provided")
        UUID employeeId,
        @NotEmpty(message = "Trip id must be provided")
        UUID tripId) {

}
