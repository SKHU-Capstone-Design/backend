package capstone.dto;

import capstone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String loginId;
    private String name;
    private String gender;
    private Integer age;
    private String phone;


    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getLoginId(), user.getName(), user.getGender(), user.getAge(), user.getPhone());
    }
}
