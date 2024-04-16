package capstone.controller;

import capstone.dto.DiaryRequestDto;
import capstone.dto.DiaryResponseDto;
import capstone.dto.DiarySummaryDto;
import capstone.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/save")
    public ResponseEntity<DiaryResponseDto> createDiary(@RequestBody DiaryRequestDto diaryRequestDto, Principal principal) {
        try {
            // Principal을 이용하여 현재 로그인한 사용자의 정보를 가져올 수 있음
            // 여기에서는 간단하게 사용자의 ID만 가져왔다고 가정
            Long userId = Long.parseLong(principal.getName());

            // PostService를 사용하여 게시글 생성
            DiaryResponseDto createdDiary = diaryService.createDiary(diaryRequestDto, userId);

            return new ResponseEntity<>(createdDiary, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/findByUserAndDate")
    public ResponseEntity<List<DiaryResponseDto>> getPostsByUserAndDate(Principal principal,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            // Principal을 이용하여 현재 로그인한 사용자의 정보를 가져올 수 있음
            // 여기에서는 간단하게 사용자의 ID만 가져왔다고 가정
            Long userId = Long.parseLong(principal.getName());

            // PostService를 사용하여 사용자의 ID와 날짜로 게시글 조회
            List<DiaryResponseDto> diarys = diaryService.getPostsByUserAndDate(userId, date);

            if (diarys.isEmpty()) {
                // 해당 날짜에 일기가 없을 경우 예외 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return new ResponseEntity<>(diarys, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<List<DiarySummaryDto>> getDiaries(@RequestParam(defaultValue = "0") int page) {
        int pageSize = 10; // 한 페이지에 표시할 일기 수
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<DiarySummaryDto> diaryPage = diaryService.getDiaries(pageable);

        // 현재 페이지가 마지막 페이지인지 확인하여 다음 페이지 번호를 계산
        int nextPage = diaryPage.hasNext() ? page + 1 : -1;

        // 클라이언트에게 다음 페이지 번호를 반환
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Next-Page", String.valueOf(nextPage));

        return ResponseEntity.ok().headers(headers).body(diaryPage.getContent());
    }

}
