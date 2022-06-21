package paloit.training.sp02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.Room;
import paloit.training.sp02.model.AvailableRoomRequest;
import paloit.training.sp02.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<Room> getRooms() {
        List<Room> result = new ArrayList<>();
        roomRepository.findAll().forEach(result::add);
        return result;
    }

    @GetMapping("/available")
    public List<Room> getAvailableRoom(@RequestBody AvailableRoomRequest request) {
        List<Room> room = roomRepository.findAvailableRoomBooking(request.getStarttime(), request.getEndtime(), request.getNbpeople());
        return room;
    }
}
