package engine.controller;

import engine.model.AppUser;
import engine.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository repository,
                          PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "api/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        if (repository.existsByUsername(request.email())) return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        if (!request.email().matches(".+@.+\\..+")) return ResponseEntity.badRequest().body(Map.of("error", "Invalid email format"));
        if (request.password.length() < 5) return ResponseEntity.badRequest().body(Map.of("error", "Password must be at least 5 characters"));

        var user = new AppUser();
        user.setUsername(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setAuthority("ROLE_USER");

        repository.save(user);

        return ResponseEntity.ok().build();
    }

    public record RegistrationRequest(
            String email,
            String password
    ) {}
}
