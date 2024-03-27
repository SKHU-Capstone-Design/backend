package capstone.domain;

import capstone.dto.DiaryResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_ID")
    private Long diaryId;
    @Column(name = "DIARY_TITLE")
    private String title;
    @Column(name = "DIARY_BODY")
    private String body;
    @Column(name="CREATED_DATE")
    private String date;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private Date currentDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Diary(String title, String body,String date, User user){
        this.title=title;
        this.body=body;
        this.user=user;
        this.date=date;
    }
    public DiaryResponseDto toDto(){
        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .title(this.getTitle())
                .body(this.getBody())
                .currentDate(this.getCurrentDate())
                .date(this.getDate())
                .build();
        return diaryResponseDto;
    }
}
