package paloit.training.sp02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.Room;
import paloit.training.sp02.exception.NotAuthorizedException;
import paloit.training.sp02.model.BookingRequest;
import paloit.training.sp02.model.LoginResponse;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.service.BookingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/booking")
public class BookingController {
    private final BookingService bookingService;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings() {
        List<Booking> result = new ArrayList<>();
        bookingRepository.findAll().forEach(result::add);
        return result;
    }

    @PostMapping
    public ResponseEntity createBooking(@RequestBody BookingRequest request) {
        Boolean checkAvaiable = bookingService.isAvaibleRoom(request.getStarttime(), request.getEndtime(), request.getNbpeople(), request.getRoom_id());
        if (!checkAvaiable) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Booking is invalid");
        } else {
            bookingService.createBooking(request);
            return ResponseEntity.ok("created booking");
        }
    }
}
