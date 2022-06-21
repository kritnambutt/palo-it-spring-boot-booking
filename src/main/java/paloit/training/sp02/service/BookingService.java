package paloit.training.sp02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.Room;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.exception.NotAuthorizedException;
import paloit.training.sp02.model.BookingRequest;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.repository.RoomRepository;
import paloit.training.sp02.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    // like a initialize class BookingService
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Boolean isAvaibleRoom(
            LocalDateTime starttime,
            LocalDateTime endtime,
            Integer nbpeople,
            Long room_id
    ) {
        List<Room> room = roomRepository.findAvailableRoomBooking(starttime, endtime, nbpeople, room_id);
        System.out.println(room.size());
        if (room.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void createBooking(BookingRequest request) {
        Optional<User> user = userRepository.findById(request.getUser_id());
        if (user.isEmpty()) {
            throw new IllegalStateException("user with user_id" + request.getUser_id() + " doesn't exist");
        }

        Optional<Room> room = roomRepository.findById(request.getRoom_id());
        if (room.isEmpty()) {
            throw new IllegalStateException("room id = " + request.getRoom_id() + " doesn't exist");
        }

        Booking newBooking = new Booking();
        newBooking.setStart(request.getStarttime());
        newBooking.setEnd(request.getEndtime());
        newBooking.setRoom(room.get());
        newBooking.setUser(user.get());
        newBooking.setNbpeople(request.getNbpeople());
        newBooking.setCreateDt(new Date());
        newBooking.setUpdateDt(new Date());
        bookingRepository.save(newBooking);
    }
}
