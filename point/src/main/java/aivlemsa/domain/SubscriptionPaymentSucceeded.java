package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionPaymentSucceeded extends AbstractEvent {

    private Long userId;
    private Integer points;

    public SubscriptionPaymentSucceeded(Point aggregate) {
        super(aggregate);
    }

    public SubscriptionPaymentSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
