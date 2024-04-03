package capstone.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVATAR_ID")
    private Long id;

    @Column(name = "AVATAR_NAME")
    private String avatarName;

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL)
    private List<Diary> diaries;

    @Column(name = "EXPERIENCE_POINTS")
    private int experiencePoints;

    @Column(name = "LEVEL")
    private int level;

    @Builder
    public Avatar(String avatarName, User user, List<Diary> diaries, int experiencePoints, int level){
        this.avatarName=avatarName;
        this.user=user;
        this.diaries=diaries;
        this.experiencePoints=experiencePoints;
        this.level=level;
    }
}