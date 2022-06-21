package paloit.training.sp02.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import paloit.training.sp02.dbmodel.Booking;
import paloit.training.sp02.dbmodel.Room;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.model.BookingRequest;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.repository.RoomRepository;
import paloit.training.sp02.repository.UserRepository;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    private User user;

    private Booking booking;

    private Room room;

//    @BeforeAll
//    static void init() {
//        new PostgreSQLContainer("postgres:12")
//                .withDatabaseName("springboot")
//                .withUsername("springboot")
//                .withPassword("springboot")
//                .start();
//    }

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
    public void should_booking() {
        BookingRequest createBooking = new BookingRequest();
        createBooking.setStarttime(LocalDateTime.of(2022,6,16,10,0));
        createBooking.setEndtime(LocalDateTime.of(2022,6,16,11,0));
        createBooking.setNbpeople(5);
        createBooking.setRoom_id(room.getId());
        createBooking.setUser_id(user.getId());

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post("/api/booking")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_return_booking_is_invalid_when_create_booking_overlaps() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime starttime = LocalDateTime.of(2022, 6, 16, 13, 0);
        LocalDateTime endtime = LocalDateTime.of(2022, 6, 16, 15, 0);

        BookingRequest createBooking = new BookingRequest();
        createBooking.setStarttime(LocalDateTime.of(2022,6,16,13,0));
        createBooking.setEndtime(LocalDateTime.of(2022,6,16,15,0));
        createBooking.setNbpeople(5);
        createBooking.setRoom_id(room.getId());
        createBooking.setUser_id(user.getId());

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post("/api/booking")
                .then()
                .statusCode(403);
//                .body("message", equalTo("Booking is invalid"));
    }

    @Test
    public void should_return_booking_is_invalid_when_create_booking_room_not_exist() {
        BookingRequest createBooking = new BookingRequest();
        createBooking.setStarttime(LocalDateTime.of(2022,6,16,10,0));
        createBooking.setEndtime(LocalDateTime.of(2022,6,16,11,0));
        createBooking.setNbpeople(5);
        createBooking.setRoom_id(5L);
        createBooking.setUser_id(user.getId());

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post("/api/booking")
                .then()
                .statusCode(403);
//                .body("message", equalTo("Booking is invalid"));
    }

    @Test
    public void should_return_booking_is_invalid_when_create_booking_user_not_exist() {
        BookingRequest createBooking = new BookingRequest();
        createBooking.setStarttime(LocalDateTime.of(2022,6,16,10,0));
        createBooking.setEndtime(LocalDateTime.of(2022,6,16,11,0));
        createBooking.setNbpeople(5);
        createBooking.setRoom_id(room.getId());
        createBooking.setUser_id(5L);

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post("/api/booking")
                .then()
                .statusCode(403);
//                .body("message", equalTo("Booking is invalid"));
    }

    @Test
    public void should_return_all_bookings() {
        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .when().get("/api/booking")
                .then()
                .statusCode(200);
    }
}
