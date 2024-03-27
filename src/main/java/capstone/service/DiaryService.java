package capstone.service;

import capstone.domain.Diary;
import capstone.domain.User;
import capstone.dto.DiaryRequestDto;
import capstone.dto.DiaryResponseDto;
import capstone.repository.DiaryRepository;
import capstone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

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
}
