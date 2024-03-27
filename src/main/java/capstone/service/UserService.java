package capstone.service;

import capstone.domain.User;
import capstone.dto.Token;
import capstone.dto.UserRequestDto;
import capstone.dto.UserResponseDto;
import capstone.jwt.TokenProvider;
import capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserResponseDto  signup(UserRequestDto userRequestDto){
             if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 사용자입니다.");
        }
            User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }
    @Transactional
    public Token login(UserRequestDto userRequestDto){
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return  tokenProvider.generateTokenDto(authentication);
    }

    @Transactional
    public Optional<User> getUserById(long id) {
            return userRepository.findUserById(id);
    }
}
