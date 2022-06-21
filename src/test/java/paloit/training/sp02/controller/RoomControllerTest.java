package paloit.training.sp02.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.Room;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.model.AvailableRoomRequest;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.repository.RoomRepository;
import paloit.training.sp02.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Room room;

    private User user;

    private Booking booking;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;

        bookingRepository.deleteAll();
        userRepository.deleteAll();
        roomRepository.deleteAll();

        User userToCreate = new User();
        userToCreate.setEmail("knambutt555@palo-it.com");
        userToCreate.setPassword("mysecretpassword");
        userToCreate.setFirstName("krit");
        userToCreate.setLastName("nambutt");
        userToCreate.setCreateDt(new Date());
        userToCreate.setUpdateDt(new Date());
        user = userRepository.save(userToCreate);

        Room roomToCreate = new Room();
        roomToCreate.setRoomName("room_1");
        roomToCreate.setRoomType("room_type_1");
        roomToCreate.setMaxSize(10);
        roomToCreate.setCreateDt(new Date());
        roomToCreate.setUpdateDt(new Date());
        room = roomRepository.save(roomToCreate);

        Booking bookingToCreate = new Booking();
        bookingToCreate.setStart(LocalDateTime.of(2022,6,16,13,0));
        bookingToCreate.setEnd(LocalDateTime.of(2022,6,16,15,0));
        bookingToCreate.setRoom(room);
        bookingToCreate.setUser(user);
        bookingToCreate.setCreateDt(new Date());
        bookingToCreate.setUpdateDt(new Date());
        booking = bookingRepository.save(bookingToCreate);
    }

    @Test
    public void should_getAllRooms_response200() {
        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .when().get("/api/room")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_getAllAvailableRooms_response200() {
        AvailableRoomRequest request = new AvailableRoomRequest();
        request.setStarttime(LocalDateTime.of(2022,6,16,10,0));
        request.setEndtime(LocalDateTime.of(2022,6,16,11,0));
        request.setNbpeople(5);

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(request)
                .when().get("/api/room/available")
                .then()
                .statusCode(200);
    }
}
