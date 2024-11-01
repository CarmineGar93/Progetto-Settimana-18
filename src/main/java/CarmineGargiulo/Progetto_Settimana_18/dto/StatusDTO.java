package CarmineGargiulo.Progetto_Settimana_18.dto;

import jakarta.validation.constraints.Pattern;

public record StatusDTO(
        @Pattern(regexp = "IN_PROGRAM|ASSIGNED|COMPLETED", message = "State must be IN_PROGESS, ASSIGNED COMPLETED") String status) {
}
