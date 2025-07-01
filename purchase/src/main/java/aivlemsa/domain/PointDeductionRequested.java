package aivlemsa.domain;

import aivlemsa.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@ToString
@NoArgsConstructor
public class PointDeductionRequested extends AbstractEvent {

    private Long userId;
    private Integer amount;
    private Long purchaseId;

    public PointDeductionRequested(Long userId, Integer amount, Long purchaseId) {
        this.userId = userId;
        this.amount = amount;
        this.purchaseId = purchaseId;
    }
}
