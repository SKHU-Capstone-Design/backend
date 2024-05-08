package capstone.service;

import capstone.domain.Avatar;
import capstone.domain.Diary;
import capstone.domain.User;
import capstone.dto.DiaryRequestDto;
import capstone.dto.DiaryResponseDto;
import capstone.dto.DiarySummaryDto;
import capstone.repository.DiaryRepository;
import capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final AvatarService avatarService;

    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, Long userId){
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Diary newDiary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .body(diaryRequestDto.getBody())
                .date(diaryRequestDto.getDate())
                .user(user)
                .build();

        Diary savedDiary = diaryRepository.save(newDiary);
        Avatar avatar = user.getAvatar();
        avatar = avatarService.updateExp(avatar.getId(), 20);
        return savedDiary.toDto();
    }

    @Transactional
    public List<DiaryResponseDto> getPostsByUserAndDate(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Diary> diarys = diaryRepository.findByUserAndCreatedAt(user, date);

        return diarys.stream()
                .map(Diary::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public Page<DiarySummaryDto> getDiaries(Pageable pageable) {
        Page<Object[]> diaryPage = diaryRepository.findDiaries(pageable);
        List<DiarySummaryDto> diaryDtos = diaryPage.stream()
                .map(array -> new DiarySummaryDto((Long) array[0], (String) array[1]))
                .collect(Collectors.toList());
        return new PageImpl<>(diaryDtos, pageable, diaryPage.getTotalElements());
    }

    @Transactional
    public DiaryResponseDto updateDiary(Long diaryId, DiaryRequestDto diaryRequestDto) {
        Diary diaryToUpdate = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 일기를 찾을 수 없습니다."));

        Diary updatedDiary = new Diary(diaryRequestDto.getTitle(), diaryRequestDto.getBody(), diaryRequestDto.getDate());
        updatedDiary.setDiaryId(diaryId); // 업데이트할 일기의 ID 설정

        Diary savedDiary = diaryRepository.save(updatedDiary);
        return savedDiary.toDto();
    }
}
