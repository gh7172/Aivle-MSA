package aivlemsa.domain;

import aivlemsa.SubscriptionApplication;
import aivlemsa.domain.SubscriptionCanceled;
import aivlemsa.domain.SubscriptionRequested;
import aivlemsa.domain.UserSubUpdated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "Subscribe_table")
@Data
//<<< DDD / Aggregate Root
public class Subscribe {

    @Id
    private Long userId;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date subscriptionExpiryDate;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        // aivlemsa.external.Point point = new aivlemsa.external.Point();
        // // mappings goes here
        // SubscriptionApplication.applicationContext
        //     .getBean(aivlemsa.external.PointService.class)
        //     .useSubscriptionPoints(point);

        SubscriptionRequested subscriptionRequested = new SubscriptionRequested(
            this
        );
        subscriptionRequested.publishAfterCommit();

        SubscriptionCanceled subscriptionCanceled = new SubscriptionCanceled(
            this
        );
        subscriptionCanceled.publishAfterCommit();

        UserSubUpdated userSubUpdated = new UserSubUpdated(this);
        userSubUpdated.publishAfterCommit();
    }

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = SubscriptionApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateState(
        SubscriptionPaymentSucceeded subscriptionPaymentSucceeded
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscribe subscribe = new Subscribe();
        repository().save(subscribe);

        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionPaymentSucceeded.get???()).ifPresent(subscribe->{
            
            subscribe // do something
            repository().save(subscribe);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateState(
        SubscriptionPaymentFailed subscriptionPaymentFailed
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscribe subscribe = new Subscribe();
        repository().save(subscribe);

        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionPaymentFailed.get???()).ifPresent(subscribe->{
            
            subscribe // do something
            repository().save(subscribe);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
