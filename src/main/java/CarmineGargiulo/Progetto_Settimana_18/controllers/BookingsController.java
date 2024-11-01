package CarmineGargiulo.Progetto_Settimana_18.controllers;

import CarmineGargiulo.Progetto_Settimana_18.dto.BookingDTO;
import CarmineGargiulo.Progetto_Settimana_18.entities.Booking;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_18.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;

    @GetMapping
    public Page<Booking> getAllBookings(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "trip.date") String sortBy) {
        return bookingsService.findAllBookings(page, size, sortBy);
    }

    @PostMapping
    public Booking saveBooking(@RequestBody @Validated BookingDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return bookingsService.saveBooking(body);
    }

    @GetMapping("/{bookingId}")
    public Booking getSingleBooking(@PathVariable UUID bookingId) {
        return bookingsService.findBookingById(bookingId);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable UUID bookingId) {
        bookingsService.findBookingByIdAndDelete(bookingId);
    }
}
