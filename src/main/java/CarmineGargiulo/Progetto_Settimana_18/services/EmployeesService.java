package CarmineGargiulo.Progetto_Settimana_18.services;

import CarmineGargiulo.Progetto_Settimana_18.dto.EmployeeDTO;
import CarmineGargiulo.Progetto_Settimana_18.entities.Employee;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_18.repositories.EmployeesRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private Cloudinary cloudinary;

    public List<Employee> findAllEmployees() {
        return employeesRepository.findAll();
    }

    public Employee saveEmployee(EmployeeDTO body) {
        if (employeesRepository.existsByUsername(body.username()))
            throw new BadRequestException("Username already in use");
        if (employeesRepository.existsByEmail(body.email())) throw new BadRequestException("Email already in use");
        return employeesRepository.save(new Employee(body.username(), body.name(), body.surname(), body.email()));
    }

    public Employee findEmployeeById(UUID employeeId) {
        return employeesRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId, "Employee"
        ));
    }

    public Employee findEmployeeByIdAndUpdate(UUID employeeId, EmployeeDTO body) {
        Employee searched = findEmployeeById(employeeId);
        if (!searched.getSurname().equals(body.surname()) || !searched.getName().equals(body.name())) {
            if (searched.getAvatarUrl().equals("https://ui-avatars.com/api/?name=" + searched.getName() + "+" + searched.getSurname()))
                searched.setAvatarUrl("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        }
        if (!body.username().equals(searched.getUsername())) {
            if (employeesRepository.existsByUsername(body.username()))
                throw new BadRequestException("Username already in use");
            searched.setUsername(body.username());
        }
        if (!body.email().equals(searched.getEmail())) {
            if (employeesRepository.existsByEmail(body.email())) throw new BadRequestException("Email already in use");
            searched.setEmail(body.email());
        }
        searched.setName(body.name());
        searched.setSurname(body.surname());
        return employeesRepository.save(searched);
    }

    public void findEmployeeByIdAndDelete(UUID employeeId) {
        Employee searched = findEmployeeById(employeeId);
        employeesRepository.delete(searched);
    }

    public void uploadAvatar(MultipartFile file, UUID employeeId) {
        try {
            Employee searched = findEmployeeById(employeeId);
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            searched.setAvatarUrl(url);
            employeesRepository.save(searched);
        } catch (IOException e) {
            throw new BadRequestException("File provided not supported");
        }
    }

}
