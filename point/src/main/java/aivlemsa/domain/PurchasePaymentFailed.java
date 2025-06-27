package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PurchasePaymentFailed extends AbstractEvent {

    private Long userId;
    private Integer points;
    private String state;

    public PurchasePaymentFailed(Point aggregate) {
        super(aggregate);
    }

    public PurchasePaymentFailed() {
        super();
    }
}
//>>> DDD / Domain Event
