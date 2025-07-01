package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchasePaymentSucceeded extends AbstractEvent {

    private Long userId;
    private Integer points;

    public PurchasePaymentSucceeded(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
    }

    public PurchasePaymentSucceeded() {
        super();
    }
}
