package capstone.dto;

import lombok.Data;

@Data
public class DiaryRequestDto {
    private String title;
    private String body;
    private String date;
}
