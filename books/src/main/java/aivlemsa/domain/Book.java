package aivlemsa.domain;

import aivlemsa.BooksApplication;
import aivlemsa.domain.BookAddFailed;
import aivlemsa.domain.BookAdded;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book_table")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String userId;

    private String title;

    private String publishDate;

    private String plot;

    private String summary;

    private String coverImage;

    private String state;

    @PostPersist
    public void onPostPersist() {
        BookAdded bookAdded = new BookAdded(this);
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
        book.setCoverImage(generationSucceeded.getImageUrl());
        book.setState(generationSucceeded.getState());
        repository().save(book);

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