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
@Table(name = "작가_table")
@Data
//<<< DDD / Aggregate Root
public class 작가 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public static 작가Repository repository() {
        작가Repository 작가Repository = AuthorApplication.applicationContext.getBean(
            작가Repository.class
        );
        return 작가Repository;
    }
}
//>>> DDD / Aggregate Root
