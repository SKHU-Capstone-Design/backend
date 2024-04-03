package capstone.dto;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarResponseDto {
    private Long id;
    private String avatarName;
    private int experiencePoints;
    private int level;
}
