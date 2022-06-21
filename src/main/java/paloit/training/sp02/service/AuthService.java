package paloit.training.sp02.service;

import org.springframework.stereotype.Service;
import paloit.training.sp02.dbmodel.User;
import paloit.training.sp02.exception.NotAuthorizedException;
import paloit.training.sp02.model.LoginRequest;
import paloit.training.sp02.model.LoginResponse;
import paloit.training.sp02.model.SignupRequest;
import paloit.training.sp02.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

   // like a initialize class AuthService
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    throw new IllegalStateException("Email is not exist");
                });

        if (request.getPassword().equals(user.getPassword())) {
            LoginResponse response = new LoginResponse();
            response.setAccessToken("token");
            return response;
        } else {
            throw new IllegalStateException("Incorrect password");
        }
    }

    public User signup(SignupRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        User createNewUser = userRepository.save(newUser);
        return createNewUser;
    }
}
