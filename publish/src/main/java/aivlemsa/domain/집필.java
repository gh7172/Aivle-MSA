package aivlemsa.domain;

import aivlemsa.PublishApplication;
import aivlemsa.domain.BookPublicationRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "집필_table")
@Data
//<<< DDD / Aggregate Root
public class 집필 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private Long userId;

    private String title;

    private Date publishDate;

    private String summary;

    private String state;

    @PostPersist
    public void onPostPersist() {
        BookPublicationRequested bookPublicationRequested = new BookPublicationRequested(
            this
        );
        bookPublicationRequested.publishAfterCommit();
    }

    public static 집필Repository repository() {
        집필Repository 집필Repository = PublishApplication.applicationContext.getBean(
            집필Repository.class
        );
        return 집필Repository;
    }
}
//>>> DDD / Aggregate Root
