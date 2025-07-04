package aivlemsa.application.dto;

import aivlemsa.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookSimpleDto {
    private Long bookId;
    private Long userId;
    private String title;
    private LocalDate publishDate;
    private String summary;
    private String coverImage;
    private String state;
    private int viewCount;

    // Book 엔티티를 DTO로 쉽게 변환하기 위한 생성자
    public BookSimpleDto(Book book) {
        this.bookId = book.getBookId();
        this.userId = book.getUserId();
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
        this.summary = book.getSummary();
        this.coverImage = book.getCoverImage();
        this.state = book.getState();
        this.viewCount = book.getViewCount();
    }
}
