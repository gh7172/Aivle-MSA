package aivlemsa.domain;

import aivlemsa.UserApplication;
import aivlemsa.domain.FailUserLoggedIn;
import aivlemsa.domain.OnUserLoggedIn;
import aivlemsa.domain.SignedUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "유저_table")
@Data
//<<< DDD / Aggregate Root
public class 유저 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String loginId;

    private String password;

    private Boolean isAuthor;

    @PostPersist
    public void onPostPersist() {
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();

        OnUserLoggedIn onUserLoggedIn = new OnUserLoggedIn(this);
        onUserLoggedIn.publishAfterCommit();

        FailUserLoggedIn failUserLoggedIn = new FailUserLoggedIn(this);
        failUserLoggedIn.publishAfterCommit();
    }

    public static 유저Repository repository() {
        유저Repository 유저Repository = UserApplication.applicationContext.getBean(
            유저Repository.class
        );
        return 유저Repository;
    }

    //<<< Clean Arch / Port Method
    public static void updateState(
        AuthorRegistrationApproved authorRegistrationApproved
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        유저 유저 = new 유저();
        repository().save(유저);

        */

        /** Example 2:  finding and process
        

        repository().findById(authorRegistrationApproved.get???()).ifPresent(유저->{
            
            유저 // do something
            repository().save(유저);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
