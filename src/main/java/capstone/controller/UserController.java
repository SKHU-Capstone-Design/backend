package capstone.controller;

import capstone.domain.User;
import capstone.dto.Token;
import capstone.dto.UserRequestDto;
import capstone.dto.UserResponseDto;
import capstone.jwt.TokenProvider;
import capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.login(userRequestDto));
    }

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Hello User");
    }

    @GetMapping("/user/info")
    public ResponseEntity<String> user(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");

        if (tokenProvider.validateToken(accessToken)) {
            org.springframework.security.core.Authentication authentication = tokenProvider.getAuthentication(accessToken);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String id = userDetails.getUsername();
            Optional<User> optionalUser = userService.getUserById(Long.parseLong(id));

            if (optionalUser.isPresent()) {
                String userEmail = optionalUser.get().getEmail();
                String username = optionalUser.get().getName();
                Integer userage = optionalUser.get().getAge();
                String userphone = optionalUser.get().getPhone();
                String userGender = optionalUser.get().getGender();
                return ResponseEntity.ok("user name: " + username + "\nuser email: " + userEmail + "\nuser gender: " + userGender + "\nuser age: " + userage + "\nuser phone: " + userphone);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}