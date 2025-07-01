package aivlemsa.domain;

import aivlemsa.AuthorApplication;
import aivlemsa.domain.AuthorRegistrationApproved;
import aivlemsa.domain.AuthorRegistrationRejected;
import aivlemsa.domain.AuthorRegistrationRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long userId;

    private String penName;

    private String portfolio;

    private String selfIntroduction;

    @PostPersist
    public void onPostPersist() {
        AuthorRegistrationApproved authorRegistrationApproved = new AuthorRegistrationApproved(
            this
        );
        authorRegistrationApproved.publishAfterCommit();

        AuthorRegistrationRejected authorRegistrationRejected = new AuthorRegistrationRejected(
            this
        );
        authorRegistrationRejected.publishAfterCommit();

        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(
            this
        );
        authorRegistrationRequested.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }
}
//>>> DDD / Aggregate Root
