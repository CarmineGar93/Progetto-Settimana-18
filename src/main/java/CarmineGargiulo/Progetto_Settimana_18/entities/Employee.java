package CarmineGargiulo.Progetto_Settimana_18.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    @Setter(AccessLevel.NONE)
    private UUID employeeId;
    @Column(nullable = false)
    private String username, name, surname, email;
    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;
    @OneToMany(mappedBy = "employee")
    @Setter(AccessLevel.NONE)
    @JsonIgnoreProperties("employee")
    private List<Booking> bookingList;

    public Employee(String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarUrl = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
    }

    @Override
    public String toString() {
        return "Employee " + employeeId +
                " = name: " + name +
                ", surname: " + surname +
                ", username: " + username +
                ", email: " + email;
    }
}