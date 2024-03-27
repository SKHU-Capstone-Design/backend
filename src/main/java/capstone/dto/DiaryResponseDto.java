package capstone.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DiaryResponseDto {
    private String title;
    private String body;
    private Date currentDate;
    private String date;
}
