package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PurchasePaymentSucceeded extends AbstractEvent {

    private Long userId;
    private Integer points;

    public PurchasePaymentSucceeded(Point aggregate) {
        super(aggregate);
    }

    public PurchasePaymentSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
