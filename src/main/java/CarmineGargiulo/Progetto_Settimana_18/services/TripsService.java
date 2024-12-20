package CarmineGargiulo.Progetto_Settimana_18.services;

import CarmineGargiulo.Progetto_Settimana_18.dto.StatusDTO;
import CarmineGargiulo.Progetto_Settimana_18.dto.TripDTO;
import CarmineGargiulo.Progetto_Settimana_18.entities.Trip;
import CarmineGargiulo.Progetto_Settimana_18.enums.TripStatus;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_18.repositories.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;


@Service
public class TripsService {
    @Autowired
    private TripsRepository tripsRepository;

    @Autowired
    private List<DateTimeFormatter> formatters;

    public Page<Trip> findAllTrips(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return tripsRepository.findAll(pageable);
    }

    public Trip saveTrip(TripDTO body) {
        Trip trip = new Trip(body.destination());
        trip.setDate(validateDate(body.date()));
        return tripsRepository.save(trip);
    }

    public Trip findTripById(UUID tripId) {
        return tripsRepository.findById(tripId).orElseThrow(() -> new NotFoundException(tripId, "Trip"));
    }

    public Trip findTripByIdAndUpdate(UUID tripId, TripDTO body) {
        Trip searched = findTripById(tripId);
        if (searched.getBooking() != null)
            throw new BadRequestException("Trip assigned to a booking. In order to modify the trip cancel the booking" +
                    " first");
        if (searched.getDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Cannot modify a trip that is past in time");
        searched.setDate(validateDate(body.date()));
        searched.setDestination(body.destination());
        return tripsRepository.save(searched);
    }

    public void findTripByIdAndDelete(UUID tripId) {
        Trip searched = findTripById(tripId);
        tripsRepository.delete(searched);
    }

    public Trip findByIdAndUpdateStatus(UUID tripId, StatusDTO body) {
        Trip searched = findTripById(tripId);
        if (TripStatus.valueOf(body.status()).equals(searched.getStatus()))
            throw new BadRequestException("Cannot provide the same status of the actual one");
        System.out.println(TripStatus.valueOf(body.status()));
        searched.setStatus(TripStatus.valueOf(body.status()));
        return tripsRepository.save(searched);
    }

    private LocalDate validateDate(String date) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate dateFormatted = LocalDate.parse(date, formatter);
                if (dateFormatted.isBefore(LocalDate.now()))
                    throw new BadRequestException("Date must be in the future");
                return dateFormatted;
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new BadRequestException("Format date not supported");
    }
}
