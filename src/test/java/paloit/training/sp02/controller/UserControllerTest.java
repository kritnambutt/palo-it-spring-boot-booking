package paloit.training.sp02.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.repository.BookingRepository;
import paloit.training.sp02.repository.RoomRepository;
import paloit.training.sp02.repository.UserRepository;

import java.util.Date;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    private User user;

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
    }

    @Test
    public void should_getAllUsers_response200() {
        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .when().get("/api/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_getUserDetail_response200() {
        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .pathParam("id", user.getId())
                .when().get("/api/user/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_getUserDetail_responseNotFound() {
        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .pathParam("id", 5)
                .when().get("/api/user/{id}")
                .then()
                .statusCode(500);
    }
}
