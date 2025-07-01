package aivlemsa.domain;

import aivlemsa.domain.*;
import aivlemsa.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class PaymentSucceeded extends AbstractEvent {
    private Long userId;
    private Integer points;

    public PaymentSucceeded(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getPoints() {
        return points;
    }
}
