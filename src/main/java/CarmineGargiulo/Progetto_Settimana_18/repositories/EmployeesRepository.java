package CarmineGargiulo.Progetto_Settimana_18.repositories;

import CarmineGargiulo.Progetto_Settimana_18.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
