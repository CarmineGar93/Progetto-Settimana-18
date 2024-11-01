package CarmineGargiulo.Progetto_Settimana_18.controllers;

import CarmineGargiulo.Progetto_Settimana_18.dto.TripDTO;
import CarmineGargiulo.Progetto_Settimana_18.entities.Trip;
import CarmineGargiulo.Progetto_Settimana_18.exceptions.BadRequestException;
import CarmineGargiulo.Progetto_Settimana_18.services.TripsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
public class TripsController {
    @Autowired
    private TripsService tripsService;

    @GetMapping
    public Page<Trip> getAllTrips(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "date") String sortBy) {
        return tripsService.findAllTrips(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip saveTrip(@RequestBody @Validated TripDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        return tripsService.saveTrip(body);
    }

    @GetMapping("/{tripId}")
    public Trip getSingleTrip(@PathVariable UUID tripId) {
        return tripsService.findTripById(tripId);
    }

    @PutMapping("/{tripId}")
    public Trip modifyTrip(@PathVariable UUID tripId, @RequestBody @Validated TripDTO body,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", ")));
        return tripsService.findTripByIdAndUpdate(tripId, body);
    }

    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@PathVariable UUID tripId) {
        tripsService.findTripByIdAndDelete(tripId);
    }

}
