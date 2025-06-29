package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@ToString
public class SubscriptionPaymentSucceeded extends AbstractEvent {

    private Long userId;
    private LocalDateTime expirationDate;
} 