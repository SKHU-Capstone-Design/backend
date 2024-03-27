package capstone.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long id;
    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;
    @Column(name = "USER_NAME", nullable = false)
    private String name;
    @Column(name = "USER_GENDER", nullable = false)
    private String gender;
    @Column(name = "USER_AGE", nullable = false)
    private Integer age;
    @Column(name = "USER_PHONE", nullable = false)
    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @Builder
    public User(String loginId, String password, String name, String gender, Integer age, String phone, Role role){
        this.loginId=loginId;
        this.password=password;
        this.name=name;
        this.gender=gender;
        this.age=age;
        this.phone=phone;
        this.role=role;
    }
}
