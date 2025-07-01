package aivlemsa.domain;

import aivlemsa.PointApplication;
import aivlemsa.domain.PurchasePaymentFailed;
import aivlemsa.domain.PurchasePaymentSucceeded;
import aivlemsa.domain.SignUpBonusPointGranted;
import aivlemsa.domain.SubscriptionPaymentFailed;
import aivlemsa.domain.SubscriptionPaymentSucceeded;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Integer points;

    @PostPersist
    public void onPostPersist() {
        SubscriptionPaymentSucceeded subscriptionPaymentSucceeded = new SubscriptionPaymentSucceeded(
            this
        );
        subscriptionPaymentSucceeded.publishAfterCommit();

        SubscriptionPaymentFailed subscriptionPaymentFailed = new SubscriptionPaymentFailed(
            this
        );
        subscriptionPaymentFailed.publishAfterCommit();

        PurchasePaymentSucceeded purchasePaymentSucceeded = new PurchasePaymentSucceeded(
            this
        );
        purchasePaymentSucceeded.publishAfterCommit();

        PurchasePaymentFailed purchasePaymentFailed = new PurchasePaymentFailed(
            this
        );
        purchasePaymentFailed.publishAfterCommit();

        SignUpBonusPointGranted signUpBonusPointGranted = new SignUpBonusPointGranted(
            this
        );
        signUpBonusPointGranted.publishAfterCommit();
    }

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public void chargePoints() {
        //implement business logic here:

        ChargedPoints chargedPoints = new ChargedPoints(this);
        chargedPoints.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

public void deductPoints(Integer price) {
    if (this.points >= price) {
        this.points -= price;

        PurchasePaymentSucceeded successEvent = new PurchasePaymentSucceeded(this);
        successEvent.publishAfterCommit();
    } else {
        PurchasePaymentFailed failedEvent = new PurchasePaymentFailed(this);
        failedEvent.publishAfterCommit();
    }
}
