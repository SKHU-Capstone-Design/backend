package capstone.controller;

import capstone.dto.AvatarRequestDto;
import capstone.dto.AvatarResponseDto;
import capstone.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping("/info")
    public ResponseEntity<AvatarResponseDto> getAvatarInfo(Principal principal) {
        try {
            Long userId = Long.parseLong(principal.getName());
            AvatarResponseDto avatarInfo = avatarService.getAvatarInfo(userId);
            return ResponseEntity.ok(avatarInfo);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/name")
    public ResponseEntity<String> setAvatarName(@RequestBody AvatarRequestDto avatarRequestDto, Principal principal) {
        try {
            if (principal == null || principal.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            Long userId = Long.parseLong(principal.getName());
            avatarService.setAvatarName(userId, avatarRequestDto.getAvatarName());
            return ResponseEntity.ok("Avatar name successfully updated");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update avatar name");
        }
    }
}

