package capstone.dto;

import capstone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String loginId;
    private String password;
    private String name;
    private String gender;
    private Integer age;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .gender(gender)
                .age(age)
                .phone(phone)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }
}
