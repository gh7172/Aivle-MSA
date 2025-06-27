package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionPaymentFailed extends AbstractEvent {

    private Long userId;
    private Integer points;
    private String state;

    public SubscriptionPaymentFailed(Point aggregate) {
        super(aggregate);
    }

    public SubscriptionPaymentFailed() {
        super();
    }
}
//>>> DDD / Domain Event
