package paloit.training.sp02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.model.LoginRequest;
import paloit.training.sp02.model.LoginResponse;
import paloit.training.sp02.model.SignupRequest;
import paloit.training.sp02.repository.UserRepository;
import paloit.training.sp02.service.AuthService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity postSignup(@RequestBody SignupRequest request) {
        Optional<User> checkEmail = userRepository.findByEmail(request.getEmail());
        if (checkEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email is already exist");
        }

        User createNewUser = authService.signup(request);
        return new ResponseEntity<>(createNewUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity postLogin(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
