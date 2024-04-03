package capstone.service;

import capstone.domain.Avatar;
import capstone.domain.User;
import capstone.dto.AvatarResponseDto;
import capstone.repository.AvatarRepository;
import capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    @Transactional
    public Avatar updateExp(Long avatarId, int exp) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new RuntimeException("아바타를 찾을 수 없습니다."));

        // 아바타의 exp 업데이트
        int currentExp = avatar.getExperiencePoints();
        int newExp = currentExp + exp;
        avatar.setExperiencePoints(newExp);

        // 아바타의 레벨 업데이트 로직 추가
        int currentLevel = avatar.getLevel();
        if (newExp >= 100) {
            avatar.setExperiencePoints(0); // 경험치 초기화
            avatar.setLevel(currentLevel + 1); // 레벨 증가
        }

        return avatarRepository.save(avatar);
    }

    public AvatarResponseDto getAvatarInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Avatar avatar = user.getAvatar();
        if (avatar == null) {
            throw new RuntimeException("사용자의 아바타를 찾을 수 없습니다.");
        }

        return AvatarResponseDto.builder()
                .avatarName(avatar.getAvatarName())
                .experiencePoints(avatar.getExperiencePoints())
                .level(avatar.getLevel())
                .build();
    }

    @Transactional
    public void setAvatarName(Long userId, String avatarName) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Avatar avatar = user.getAvatar();
        if (avatar == null) {
            // 사용자의 아바타가 없는 경우 새로운 아바타 생성
            avatar = Avatar.builder()
                    .avatarName(avatarName)
                    .user(user)
                    .build();
            user.setAvatar(avatar);
        } else {
            avatar.setAvatarName(avatarName);
        }
        userRepository.save(user);
    }

}
