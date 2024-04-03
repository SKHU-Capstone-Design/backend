package capstone.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class AvatarResponseDto {
    private String avatarName;
    private int experiencePoints;
    private int level;
}
