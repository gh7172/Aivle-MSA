package aivlemsa.domain;

import aivlemsa.ReadApplication;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import java.util.Optional;

@Entity
@Table(name = "SubscriptionModel")
@Data
public class SubscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private LocalDateTime expirationDate;
    private LocalDateTime startDate;

    public static void updateSubscription(SubscriptionPaymentSucceeded event) {
        SubscriptionModelRepository repo = ReadApplication.applicationContext.getBean(SubscriptionModelRepository.class);
        Optional<SubscriptionModel> subscription = repo.findByUserId(event.getUserId());
        SubscriptionModel entity = subscription.orElse(new SubscriptionModel());
        entity.setUserId(event.getUserId());
        entity.setExpirationDate(event.getExpirationDate());
        entity.setStartDate(LocalDateTime.now());
        repo.save(entity);
    }
} 