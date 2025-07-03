package aivlemsa.application.dto;

import aivlemsa.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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

    public BookDetailDto(Book book) {
        this.bookId = book.getBookId();
        this.userId = book.getUserId();
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
        this.text = book.getText(); // 본문 매핑
        this.summary = book.getSummary();
        this.coverImage = book.getCoverImage();
        this.state = book.getState();
        this.viewCount = book.getViewCount();
    }
}