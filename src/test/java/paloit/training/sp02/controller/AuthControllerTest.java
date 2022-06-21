package paloit.training.sp02.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.model.LoginRequest;
import paloit.training.sp02.repository.UserRepository;
import paloit.training.sp02.service.AuthService;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;

        userRepository.deleteAll();

        User userToCreate = new User();
        userToCreate.setEmail("knambutt@palo-it.com");
        userToCreate.setPassword("mysecretpassword");
        userToCreate.setFirstName("krit");
        userToCreate.setLastName("nambutt");
        userToCreate.setCreateDt(new Date());
        userToCreate.setUpdateDt(new Date());
        user = userRepository.save(userToCreate);
    }

    @Test
    public void should_register() {
        User user = new User();
        user.setEmail("knambutt888@palo-it.com");
        user.setPassword("testpassword");
        user.setFirstName("krit 2");
        user.setLastName("nambutt");

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/api/auth/sign-up")
                .then()
                .statusCode(201);
    }

    @Test
    public void should_return_already_exist_email_when_register_fails() {
        User user = new User();
        user.setEmail("knambutt@palo-it.com");
        user.setPassword("testpassword");
        user.setFirstName("krit 2");
        user.setLastName("nambutt");

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/api/auth/sign-up")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_login() {
        User user = new User();
        user.setEmail("knambutt@palo-it.com");
        user.setPassword("mysecretpassword");

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("accessToken", equalTo("token"));
    }

    @Test
    public void should_return_email_not_exist_when_login_fails() {
        User user = new User();
        user.setEmail("knambutt555@palo-it.com");
        user.setPassword("testpassword");

        given().log().all()
                .with()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post("/api/auth/login")
                .then()
                .statusCode(500);
    }

    @Test
    public void should_return_wrong_password_when_login_fails() {
        LoginRequest request = new LoginRequest();
        request.setEmail("knambutt@palo-it.com");
        request.setPassword("testpassword");

        given().log().all()
                .with().body(request).header("Content-Type", "application/json")
                .when().post("/api/auth/login")
                .then()
                .statusCode(500);
//                .assertThat()
//                .body("message", equalToIgnoringCase("Incorrect password"));
    }
}
