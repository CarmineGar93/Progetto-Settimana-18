package CarmineGargiulo.Progetto_Settimana_18.repositories;

import CarmineGargiulo.Progetto_Settimana_18.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripsRepository extends JpaRepository<Trip, UUID> {
}
