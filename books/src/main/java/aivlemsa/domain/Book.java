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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public static void addBook(GenerationSucceeded generationSucceeded) {
        //implement business logic here:

        /** Example 1:  new item */
        Book book = new Book();
        book.setBookId(generationSucceeded.getBookId());
        book.setSummary(generationSucceeded.getSummary());
        book.setCoverImage(generationSucceeded.getCoverImage());
        book.setState("add Book!");
        book.setText(generationSucceeded.getText());
        book.setPublishDate(generationSucceeded.getPublishDate());
        book.setTitle(generationSucceeded.getTitle());
        book.setUserId(generationSucceeded.getUserId());
        try {
            repository().save(book);
        } catch (Exception e) {
            log.error("도서 저장 실패", e);
            BookAddFailed bookAddFailed = new BookAddFailed();
            BeanUtils.copyProperties(book, bookAddFailed);
            bookAddFailed.setState("add book Failed");
            bookAddFailed.publishAfterCommit();
        }



        /** Example 2:  finding and process */
        /*
        repository().findById(generationSucceeded.bookId).ifPresent(book->{
            book.setSummary(generationSucceeded.summary);
            book.setCoverImage(generationSucceeded.imageUrl);
            book.setState(generationSucceeded.state);
            repository().save(book);
        });
        */
    }
} 