package aivlemsa.domain;

import aivlemsa.PointApplication;
import aivlemsa.domain.PurchasePaymentFailed;
import aivlemsa.domain.PurchasePaymentSucceeded;
import aivlemsa.domain.SignUpBonusPointGranted;
import aivlemsa.domain.SubscriptionPaymentFailed;
import aivlemsa.domain.SubscriptionPaymentSucceeded;
import aivlemsa.domain.UsedPoints;

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

    public void chargePoints(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전할 포인트는 0보다 커야 합니다.");
        }

        if (this.points == null) {
            this.points = 0;
        }

        this.points += amount;

        ChargedPoints chargedPoints = new ChargedPoints(this);
        chargedPoints.publishAfterCommit();
    }


    public void usePoints(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("차감할 포인트는 0보다 커야 합니다.");
        }

        if (this.points == null || this.points < amount) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        this.points -= amount;

        UsedPoints usedPoints = new UsedPoints(this);
        usedPoints.publishAfterCommit();
    }
}
