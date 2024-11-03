package CarmineGargiulo.Progetto_Settimana_18.services;

import CarmineGargiulo.Progetto_Settimana_18.dto.BookingDTO;
import CarmineGargiulo.Progetto_Settimana_18.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_18.entities.Employee;
import CarmineGargiulo.Progetto_Settimana_18.entities.Trip;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_18.repositories.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingsService {
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private TripsService tripsService;

    public Page<Booking> findAllBookings(int page, int size, String sortBy, UUID employeeId) {
        if (employeeId != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Employee employee = employeesService.findEmployeeById(employeeId);
            return bookingsRepository.findByEmployee(employee, pageable);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookingsRepository.findAll(pageable);
    }

    public Booking saveBooking(BookingDTO body) {
        Employee employee = employeesService.findEmployeeById(body.employeeId());
        Trip trip = tripsService.findTripById(body.tripId());
        if (bookingsRepository.existsByTrip(trip))
            throw new BadRequestException("Trip already assigned");
        if (bookingsRepository.checkIfEmployeeIsNotAvailable(employee, trip.getDate()))
            throw new BadRequestException("Employee unavailable for trip's date");
        Booking booking = new Booking(trip, employee);
        if (body.preferences() != null) booking.setPreferences(body.preferences());
        else booking.setPreferences("N/A");
        return bookingsRepository.save(booking);
    }

    public Booking findBookingById(UUID bookingId) {
        return bookingsRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId, "booking"));
    }

    public void findBookingByIdAndDelete(UUID bookingId) {
        Booking booking = findBookingById(bookingId);
        bookingsRepository.delete(booking);
    }
}
