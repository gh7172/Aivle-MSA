package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchasePaymentFailed extends AbstractEvent {

    private Long userId;
    private Integer points;
    private String state;

    public PurchasePaymentFailed(Point aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.points = aggregate.getPoints();
        this.state = "FAILURE"; // 상태 문자열은 실제 비즈니스 요구에 맞게 설정
    }

    public PurchasePaymentFailed() {
        super();
    }
}