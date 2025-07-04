package aivlemsa.domain;

import aivlemsa.BooksApplication;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Entity
@Table(name = "book_table")
@Data
@Slf4j
public class Book {

    @Id
    @JsonProperty()
    private Long bookId;

    private Long userId;

    private String title;

    private LocalDate publishDate;

    @Lob
    private String text;

    @Column(length = 2000)
    private String summary;

    @Column(length = 1000)
    private String coverImage;

    private String state;

    private int viewCount;

    @PostPersist
    public void onPostPersist() {
        BookAdded bookAdded = new BookAdded(this);
        bookAdded.setState("add book succeeded");
        bookAdded.publishAfterCommit();
    }

    public static BookRepository repository() {
        BookRepository bookRepository = BooksApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }
} 