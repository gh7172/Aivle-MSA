package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@ToString
public class UserSubUpdated extends AbstractEvent {

    private Long timestamp;
    private Long userId;
    private LocalDateTime subscriptionExpiryDate;
} 