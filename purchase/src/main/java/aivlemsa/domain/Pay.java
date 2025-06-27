package aivlemsa.domain;

import aivlemsa.PurchaseApplication;
import aivlemsa.domain.BookPurchaseRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Pay_table")
@Data
//<<< DDD / Aggregate Root
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private Long userId;

    private String bookId;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        aivlemsa.external.Point point = new aivlemsa.external.Point();
        // mappings goes here
        PurchaseApplication.applicationContext
            .getBean(aivlemsa.external.PointService.class)
            .usePurchasePoints(point);

        BookPurchaseRequested bookPurchaseRequested = new BookPurchaseRequested(
            this
        );
        bookPurchaseRequested.publishAfterCommit();
    }

    public static PayRepository repository() {
        PayRepository payRepository = PurchaseApplication.applicationContext.getBean(
            PayRepository.class
        );
        return payRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateState(
        PurchasePaymentSucceeded purchasePaymentSucceeded
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Pay pay = new Pay();
        repository().save(pay);

        */

        /** Example 2:  finding and process
        

        repository().findById(purchasePaymentSucceeded.get???()).ifPresent(pay->{
            
            pay // do something
            repository().save(pay);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateState(
        PurchasePaymentFailed purchasePaymentFailed
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Pay pay = new Pay();
        repository().save(pay);

        */

        /** Example 2:  finding and process
        

        repository().findById(purchasePaymentFailed.get???()).ifPresent(pay->{
            
            pay // do something
            repository().save(pay);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
