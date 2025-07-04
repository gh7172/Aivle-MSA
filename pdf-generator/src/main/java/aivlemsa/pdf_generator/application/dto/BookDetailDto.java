package aivlemsa.pdf_generator.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailDto {
    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String text; // 본문(text) 필드 추가
    private String summary;
    private String coverImage;
    private String state;
    private int viewCount;

}