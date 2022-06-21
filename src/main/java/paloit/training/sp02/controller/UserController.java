package paloit.training.sp02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public UserController(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable("id") Long id) {
        userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        User user = userRepository.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/booking")
    public List<Booking> getUserBookingHistory(@PathVariable("id") Long id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            User user = userRepository.getById(id);
            List<Booking> booking = bookingRepository.findAllByUser(user);
            return booking;
        } else {
            throw new IllegalStateException("User not found: " + id);
        }
    }
}
