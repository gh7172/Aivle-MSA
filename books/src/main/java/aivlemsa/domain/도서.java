package aivlemsa.domain;

import aivlemsa.BooksApplication;
import aivlemsa.domain.BookAddFailed;
import aivlemsa.domain.BookAdded;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "도서_table")
@Data
//<<< DDD / Aggregate Root
public class 도서 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String userId;

    private String title;

    private String publishDate;

    private String plot;

    private String summary;

    private String coverImage;

    @PostPersist
    public void onPostPersist() {
        BookAdded bookAdded = new BookAdded(this);
        bookAdded.publishAfterCommit();

        BookAddFailed bookAddFailed = new BookAddFailed(this);
        bookAddFailed.publishAfterCommit();
    }

    public static 도서Repository repository() {
        도서Repository 도서Repository = BooksApplication.applicationContext.getBean(
            도서Repository.class
        );
        return 도서Repository;
    }
}
//>>> DDD / Aggregate Root
